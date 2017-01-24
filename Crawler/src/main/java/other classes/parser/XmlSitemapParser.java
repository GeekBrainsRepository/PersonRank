package geekbrains.internship.project_full.Crawler.src.main.java.other_classes.parser;


import geekbrains.internship.project_full.Crawler.src.main.java.other_classes.parser.model.*;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;


class XmlSitemapParser implements ISitemapParser {
    private final InputStream inputStream;
    private final String urlPrefix;
    private final boolean continueOnErrors;
    private final Date minLastMod;
    private XmlSitemapHandler xmlSitemapHandler;

    public XmlSitemapParser(InputStream inputStream, String urlPrefix, boolean continueOnErrors, Date minLastMod) {
        this.inputStream = inputStream;
        this.urlPrefix = urlPrefix;
        this.continueOnErrors = continueOnErrors;
        this.minLastMod = minLastMod;
    }

    @Override
    public void parse() {
        SAXParserFactory factory;
        try {
            //We want the default JDK XML parser (which is even in the openJDK) by default as the Xerces XML parser does
            //not seem to be able to parse certain XML files. This is only relevant when the Apache Xerces library is
            //also in the classpath and would be chosen first otherwise. If this fails we revert back to the default
            //XML parser which might then be Apache Xerces...
            factory = SAXParserFactory.newInstance("com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl", null);
        } catch (FactoryConfigurationError e) {
            factory = SAXParserFactory.newInstance();
        }
        SAXParser saxParser;
        try {
            saxParser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            throw new SitemapParseException("Error creating sitemap parser.", e);
        }
        xmlSitemapHandler = new XmlSitemapHandler(urlPrefix, minLastMod, continueOnErrors);
        try {
            InputStreamReader inputReader = new InputStreamReader(inputStream,"UTF-8");
            InputSource inputSource = new InputSource(inputReader);
            inputSource.setEncoding("UTF-8");
            saxParser.parse(inputSource, xmlSitemapHandler);
        } catch (SAXException e) {
            throw new SitemapParseException("Error parsing sitemap.", e);
        } catch (IOException e) {
            throw new UrlConnectionException(e.getMessage());
        }
    }

    @Override
    public Set<SitemapIndex> getSitemapIndexes() {
        if (xmlSitemapHandler == null) {
            return new LinkedHashSet<>(0);
        }
        return xmlSitemapHandler.sitemapIndexes;
    }

    @Override
    public Set<SitemapEntry> getSitemapEntries() {
        if (xmlSitemapHandler == null) {
            return new LinkedHashSet<>(0);
        }
        return xmlSitemapHandler.sitemapEntries;
    }

    @Override
    public Sitemap.SitemapType getSitemapType() {
        if (xmlSitemapHandler == null) {
            return null;
        }
        return xmlSitemapHandler.sitemapType;
    }

    private static class XmlSitemapHandler extends DefaultHandler {
        private final String urlPrefix;
        private final Date minLastMod;
        private final boolean continueOnErrors;
        private Set<SitemapIndex> sitemapIndexes = new LinkedHashSet<>();
        private Set<SitemapEntry> sitemapEntries = new LinkedHashSet<>();
        private Sitemap.SitemapType sitemapType;
        private LinkedList<String> nodeNameStack = new LinkedList<>();
        private boolean skipElement = false;
        private String loc;
        private Date lastMod;
        private Set<Link> links;
        private SitemapEntry.ChangeFreq changeFreq;
        private Double priority;

        private XmlSitemapHandler(String urlPrefix, Date minLastMod, boolean continueOnErrors) {
            this.urlPrefix = urlPrefix;
            this.minLastMod = minLastMod;
            this.continueOnErrors = continueOnErrors;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            String qNameLowerCase = qName.toLowerCase();
            if (sitemapType == null && nodeNameStack.isEmpty()) {
                if (qNameLowerCase.equals("sitemapindex")) {
                    sitemapType = Sitemap.SitemapType.INDEX;
                } else if (qNameLowerCase.equals("urlset")) {
                    sitemapType = Sitemap.SitemapType.XML;
                } else {
                    throw new InvalidXmlException("Sitemap must be encapsulated in either urlset or sitemapindex.");
                }
            }
            if (qNameLowerCase.equals("sitemap")) {
                if (sitemapType != Sitemap.SitemapType.INDEX) {
                    conditionalThrow("A sitemap node is only allowed in a sitemap index.");
                }
                loc = null;
                lastMod = null;
            } else if (qNameLowerCase.equals("url")) {
                if (sitemapType != Sitemap.SitemapType.XML) {
                    conditionalThrow("A url node is only allowed in an XML sitemap.");
                }
                skipElement = false;
                loc = null;
                lastMod = null;
                links = new LinkedHashSet<>();
                changeFreq = null;
                priority = null;
            } else if (qNameLowerCase.equals("xhtml:link")) {
                String rel = attributes.getValue("rel");
                String hreflang = attributes.getValue("hreflang");
                String href = attributes.getValue("href");
                links.add(new Link(rel, href, hreflang));
            }
            nodeNameStack.push(qNameLowerCase);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            String qNameLowerCase = qName.toLowerCase();
            if (qNameLowerCase.equals("sitemap")) {
                if (loc != null) {
                    sitemapIndexes.add(new SitemapIndex(loc, lastMod));
                } else {
                    conditionalThrow("Loc must not be empty.");
                }
            } else if (qNameLowerCase.equals("url")) {
                if (!skipElement) {
                    if (loc != null) {
                        sitemapEntries.add(new SitemapEntry(loc, links, lastMod, changeFreq, priority));
                    } else {
                        conditionalThrow("Loc must not be empty.");
                    }
                }
            }
            nodeNameStack.pop();
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String nodeContent = new String(ch, start, length).trim();
            if (nodeContent.isEmpty()) {
                return;
            }
            String currentNodeName = nodeNameStack.peek();
            if (currentNodeName.equals("loc")) {
                loc = nodeContent;
                if (!loc.startsWith(urlPrefix)) {
                    skipElement = true;
                }
            } else if (currentNodeName.equals("lastmod")) {
                try {
                    lastMod = DatatypeConverter.parseDateTime(nodeContent).getTime();
                    if (minLastMod != null && lastMod.before(minLastMod)) {
                        skipElement = true;
                    }
                } catch (IllegalArgumentException e) {
                    conditionalThrow("Unable to parse last modified date " + nodeContent);
                }
            } else if (currentNodeName.equals("changefreq")) {
                if (sitemapType != Sitemap.SitemapType.XML) {
                    conditionalThrow("A changefreq node is only allowed in an XML sitemap.");
                }
                try {
                    changeFreq = SitemapEntry.ChangeFreq.valueOf(nodeContent.toLowerCase(Locale.ENGLISH));
                } catch (IllegalArgumentException e) {
                    conditionalThrow("Unable to parse change frequency " + nodeContent);
                }
            } else if (currentNodeName.equals("priority")) {
                if (sitemapType != Sitemap.SitemapType.XML) {
                    conditionalThrow("A priority node is only allowed in an XML sitemap.");
                }
                try {
                    priority = Double.parseDouble(nodeContent);
                    if (priority < 0.0 || priority > 1.0) {
                        conditionalThrow("Priority must be between 0.0 and 1.0. A value of " + priority + " is not allowed.");
                    }
                } catch (NumberFormatException e) {
                    conditionalThrow("Unable to parse priority " + nodeContent);
                }
            }
        }

        @Override
        public void fatalError(SAXParseException e) throws SAXException {
            if (nodeNameStack.isEmpty()) {
                throw new InvalidXmlException(e);
            }
            throw e;
        }

        private void conditionalThrow(String message) throws SAXException {
            if (!continueOnErrors) {
                String lastLoc = getLastLoc();
                if (lastLoc != null) {
                    throw new SAXException(lastLoc + ": " + message);
                }
                throw new SAXException(message);
            }
        }

        private String getLastLoc() {
            if (loc != null) {
                return loc;
            }
            //well yeah... get the loc of the last SitemapEntry of the Set sitemapEntries
            //probably not a very good performance, but this method is only called in case of an exception anyway
            SitemapEntry[] sitemapEntriesArray = sitemapEntries.toArray(new SitemapEntry[sitemapEntries.size()]);
            if (sitemapEntriesArray.length > 0) {
                return sitemapEntriesArray[sitemapEntriesArray.length].getLoc();
            }
            return null;
        }
    }
}
