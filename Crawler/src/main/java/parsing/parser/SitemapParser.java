package parsing.parser;


import otherclasses.parser.*;
import otherclasses.parser.HttpConnection;
import otherclasses.parser.ISitemapParser;
import otherclasses.parser.InvalidXmlException;
import otherclasses.parser.PlaintextSitemapParser;
import otherclasses.parser.XmlSitemapParser;
import otherclasses.parser.model.*;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A parser that can parse sitemaps in different formats and return a representation of the sitemap. It can also try to
 * determine the location of a sitemap. URLs can be passed as String or URL. Furthermore an InputStream may be passed
 * directly. Supported sitemaps are XML sitemaps, flat text sitemaps and sitemap indexes.
 */
public class SitemapParser {
    private String userAgent = null;
    private int timeout = 15000;
    private boolean ignoreTlsCertificates = true;
    private boolean continueOnErrors = false;

    /**
     * Set the user agent string that should be used when retrieving a sitemap or determining the location of a sitemap.
     * This setting only applies to methods that retrieve a sitemap or the locations of a sitemap, i.e when you supply
     * a URL (as String or URL) and not when you supply an InputStream directly. By default the standard Java user agent
     * string is used.
     * @param userAgent     The user agent string that should be used when retrieving a sitemap or determining the
     *                      location of a sitemap
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * Set the timeout in milliseconds for retrieving a sitemap or determining the location of a sitemap.
     * This setting only applies to methods that retrieve a sitemap or the locations of a sitemap, i.e when you supply
     * a URL (as String or URL) and not when you supply an InputStream directly. The default timeout is 15000
     * milliseconds (15 seconds).
     * @param timeout    The timeout in milliseconds for retrieving a sitemap or determining the location of a sitemap
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * Set whether TLS/SSL certificates should be ignored when retrieving a sitemap or determining the location of a
     * sitemap. This setting can be useful for URLs with self-signed certificates. Also this setting only applies to
     * methods that retrieve a sitemap or the locations of a sitemap, i.e when you supply a URL (as String or URL) and
     * not when you supply an InputStream directly. The default value for this setting is true (ignore TLS/SSL
     * certificates).
     * @param ignore    true when the TLS/SSL certificate should be ignored when retrieving a sitemap or determining
     *                  the location of a sitemap, false otheriwse
     */
    public void ignoreTlsCertificates(boolean ignore) {
        this.ignoreTlsCertificates = ignore;
    }

    /**
     * Set whether the parser should continue parsing (instead of throwing an exception) when an invalid element was
     * found or an element has an invalid value. This setting only applies to the sitemap types XML and INDEX. The
     * default setting for this value is false (don't continue on errors but throw an exception).
     * Note: It is not always possible to continue, for example for malformed XML documents. Also be aware that the
     * returned sitemap might simply contain null values for fields (in case of invalid dates, priorities or change
     * frequencies) or even that an entry is skipped entirely (in case of an empty URL).
     * @param continueOnErrors    true when the parser should continue on errors (like invalid elements or elements with
     *                            invalid values), false when it should throw an exception
     */
    public void setContinueOnErrors(boolean continueOnErrors) {
        this.continueOnErrors = continueOnErrors;
    }

    /**
     * Get the user agent string that is used when retrieving a sitemap or determining the location of a sitemap. By
     * default the standard Java user agent string is used.
     * @return  the user agent string that is used when retrieving a sitemap or determining the location of a sitemap
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * Get the timeout in milliseconds for retrieving a sitemap or determining the location of a sitemap. The default
     * timeout is 15000 milliseconds (15 seconds).
     * @return the timeout in milliseconds for retrieving a sitemap or determining the location of a sitemap
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * Get whether TLS/SSL certificates will ignored when retrieving a sitemap or determining the location of a sitemap.
     * The default value for this setting is true (ignore TLS/SSL certificates).
     * @return true when the TLS/SSL certificate will ignored when retrieving a sitemap or determining the location of a
     * sitemap, false otheriwse
     */
    public boolean isIgnoreTlsCertificates() {
        return ignoreTlsCertificates;
    }

    /**
     * Get whether the parser will continue parsing (instead of throwing an exception) when an invalid element was
     * found or an element has an invalid value. The default setting for this value is false (don't continue on errors
     * but throw an exception).
     * @return true when the parser will continue on errors (like invalid elements or elements with invalid values),
     * false when it will throw an exception
     */
    public boolean isContinueOnErrors() {
        return continueOnErrors;
    }

    /**
     * Tries to get the location of the sitemap or sitemaps by retrieving the robots.txt file of a web server and
     * scanning it for a sitemap specification.
     * @param url    any URL from a web server that should be used as basis to get the sitemap location(s). Only the
     *               hostname part of the URL is used and 'robots.txt' is appended as path. So any URL from a web server
     *               can be passed
     * @return a Set of Strings containing the URL or URLs of the sitemap or sitemaps
     * @throws otherclasses.parser.model.InvalidSitemapUrlException when either no robots.txt file was found or the file does not have a sitemap
     * specification
     * @throws otherclasses.parser.model.UrlConnectionException when there was an exception retrieving the robots.txt file or the passed URL was
     * invalid
     */
    public Set<String> getSitemapLocations(String url) {
        return getSitemapLocations(parsing.parser.HttpConnection.newUrl(url));
    }

    /**
     * Tries to get the location of the sitemap or sitemaps by retrieving the robots.txt file of a web server and
     * scanning it for a sitemap specification.
     * @param url    any URL from a web server that should be used as basis to get the sitemap location(s). Only the
     *               hostname part of the URL is used and 'robots.txt' is appended as path. So any URL from a web server
     *               can be passed
     * @return a Set of Strings containing the URL or URLs of the sitemap or sitemaps
     * @throws otherclasses.parser.model.InvalidSitemapUrlException when either no robots.txt file was found or the file does not have a sitemap
     * specification
     * @throws otherclasses.parser.model.UrlConnectionException when there was an exception retrieving the robots.txt file
     */
    public Set<String> getSitemapLocations(URL url) {
        URL robotsTxtUrl = otherclasses.parser.HttpConnection.getRobotsTxtUrl(url);
        try (otherclasses.parser.HttpConnection httpConnection = new otherclasses.parser.HttpConnection(robotsTxtUrl, userAgent, timeout, ignoreTlsCertificates)) {
            int responseCode = httpConnection.getResponseCode();
            if (responseCode < 200 || responseCode >= 300) {
                throw new InvalidSitemapUrlException("Sitemap locations could not be found. Robots.txt returned HTTP status code "
                        + responseCode + ".");
            }
            Set<String> sitemapLines = new LinkedHashSet<>();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), StandardCharsets.UTF_8));
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.length() > 8 && line.substring(0, 8).equalsIgnoreCase("sitemap:")) {
                        sitemapLines.add(line.substring(8).trim());
                    }
                }
            } catch (IOException e) {
                throw new UrlConnectionException(e.getMessage());
            }
            if (sitemapLines.isEmpty()) {
                throw new InvalidSitemapUrlException("Sitemap locations could not be found. Robots.txt does not contain Sitemap entry.");
            }
            return sitemapLines;
        }
    }

    /**
     * Parse the sitemap from the passed URL.
     * @param url    the URL to retrieve the sitemap from. When this is the URL of a sitemap index, all referenced
     *               sitemaps are also retrieved
     * @return the parsed sitemap from the passed URL
     * @throws otherclasses.parser.model.InvalidSitemapUrlException when the sitemap could not be retrieved from the passed URL
     * @throws otherclasses.parser.model.SitemapParseException when the sitemap could not be parsed
     * @throws otherclasses.parser.model.UrlConnectionException when the passed URL is invalid
     */
    public Sitemap parseSitemap(String url) {
        return parseSitemap(url, true, null);
    }

    /**
     * Parse the sitemap from the passed URL. When this is the URL of a sitemap index, the passed recursive parameter
     * determines whether all referenced sitemaps should also be retrieved. Otherwise this parameter has no effect.
     * @param url          the URL to retrieve the sitemap from
     * @param recursive    whether all referenced sitemaps in a sitemap index should also be retrieved
     * @return the parsed sitemap from the passed URL
     * @throws otherclasses.parser.model.InvalidSitemapUrlException when the sitemap could not be retrieved from the passed URL
     * @throws otherclasses.parser.model.SitemapParseException when the sitemap could not be parsed
     * @throws otherclasses.parser.model.UrlConnectionException when the passed URL is invalid
     */
    public Sitemap parseSitemap(String url, boolean recursive) {
        return parseSitemap(url, recursive, null);
    }

    /**
     * Parse the sitemap from the passed URL and return a sitemap containing only entries after the passed minLastMod
     * date. This date affects both sitemap indexes and sitemap entries. Furthermore when the lastMod date of a sitemap
     * index entry is before the passed date the whole sitemap is not considered. All entries without a lastMod (this
     * includes plain text sitemaps) are always considered.
     * @param url           the URL to retrieve the sitemap from
     * @param minLastMod    the minimal lastMod date that a sitemap entry or sitemap index entry must have to be
     *                      considered. The date must be equal or after the passed date
     * @return the sitemap from the passed URL containing only entries after the passed minLastMod date
     * @throws otherclasses.parser.model.InvalidSitemapUrlException when the sitemap could not be retrieved from the passed URL
     * @throws otherclasses.parser.model.SitemapParseException when the sitemap could not be parsed
     * @throws otherclasses.parser.model.UrlConnectionException when the passed URL is invalid
     */
    public Sitemap parseSitemap(String url, Date minLastMod) {
        return parseSitemap(url, true, minLastMod);
    }

    /**
     * Parse the sitemap from the passed URL.
     * @param url    the URL to retrieve the sitemap from. When this is the URL of a sitemap index, all referenced
     *               sitemaps are also retrieved
     * @return the parsed sitemap from the passed URL
     * @throws otherclasses.parser.model.InvalidSitemapUrlException when the sitemap could not be retrieved from the passed URL
     * @throws otherclasses.parser.model.SitemapParseException when the sitemap could not be parsed
     */
    public Sitemap parseSitemap(URL url) {
        return parseSitemap(url, true, null);
    }

    /**
     * Parse the sitemap from the passed URL. When this is the URL of a sitemap index, the passed recursive parameter
     * determines whether all referenced sitemaps should also be retrieved. Otherwise this parameter has no effect.
     * @param url          the URL to retrieve the sitemap from
     * @param recursive    whether all referenced sitemaps in a sitemap index should also be retrieved
     * @return the parsed sitemap from the passed URL
     * @throws otherclasses.parser.model.InvalidSitemapUrlException when the sitemap could not be retrieved from the passed URL
     * @throws otherclasses.parser.model.SitemapParseException when the sitemap could not be parsed
     */
    public Sitemap parseSitemap(URL url, boolean recursive) {
        return parseSitemap(url, recursive, null);
    }

    /**
     * Parse the sitemap from the passed URL and return a sitemap containing only entries after the passed minLastMod
     * date. This date affects both sitemap indexes and sitemap entries. Furthermore when the lastMod date of a sitemap
     * index entry is before the passed date the whole sitemap is not considered. All entries without a lastMod (this
     * includes plain text sitemaps) are always considered.
     * @param url           the URL to retrieve the sitemap from
     * @param minLastMod    the minimal lastMod date that a sitemap entry or sitemap index entry must have to be
     *                      considered. The date must be equal or after the passed date
     * @return the sitemap from the passed URL containing only entries after the passed minLastMod date
     * @throws otherclasses.parser.model.InvalidSitemapUrlException when the sitemap could not be retrieved from the passed URL
     * @throws otherclasses.parser.model.SitemapParseException when the sitemap could not be parsed
     */
    public Sitemap parseSitemap(URL url, Date minLastMod) {
        return parseSitemap(url, true, minLastMod);
    }

    /**
     * Parse the sitemap from the passed inputStream verifying the contained URLs against the passed URL.
     * @param inputStream    the InputStream to read the sitemap from
     * @param url            the URL that the passed inputStream belongs to. This does not have to be the actual URL as
     *                       this URL is only used to determine whether an entry is allowed in the sitemap
     * @return the parsed sitemap from the passed inputStream
     * @throws otherclasses.parser.model.InvalidSitemapUrlException when the sitemap from the passed inputStream is of type INDEX and one of the
     * referenced sitemaps could not be retrieved
     * @throws otherclasses.parser.model.SitemapParseException when the sitemap could not be parsed
     */
    public Sitemap parseSitemap(InputStream inputStream, URL url) {
        return parseSitemap(inputStream, url, true, null);
    }

    /**
     * Parse the sitemap from the passed inputStream verifying the contained URLs against the passed URL. When this is
     * the URL of a sitemap index, the passed recursive parameter determines whether all referenced sitemaps should also
     * be retrieved. Otherwise this parameter has no effect.
     * @param inputStream    the InputStream to read the sitemap from
     * @param url            the URL that the passed inputStream belongs to. This does not have to be the actual URL as
     *                       this URL is only used to determine whether an entry is allowed in the sitemap
     * @param recursive      whether all referenced sitemaps in a sitemap index should also be retrieved
     * @return the parsed sitemap from the passed inputStream
     * @throws otherclasses.parser.model.InvalidSitemapUrlException when the sitemap from the passed inputStream is of type INDEX and one of the
     * referenced sitemaps could not be retrieved
     * @throws otherclasses.parser.model.SitemapParseException when the sitemap could not be parsed
     */
    public Sitemap parseSitemap(InputStream inputStream, URL url, boolean recursive) {
        return parseSitemap(inputStream, url, recursive, null);
    }

    /**
     * Parse the sitemap from the passed inputStream verifying the contained URLs against the passed URL. This method
     * return a sitemap containing only entries after the passed minLastMod date. This date affects both sitemap indexes
     * and sitemap entries. Furthermore when the lastMod date of a sitemap index entry is before the passed date the
     * whole sitemap is not considered. All entries without a lastMod (this includes plain text sitemaps) are always
     * considered.
     * @param inputStream    the InputStream to read the sitemap from
     * @param url            the URL that the passed inputStream belongs to. This does not have to be the actual URL as
     *                       this URL is only used to determine whether an entry is allowed in the sitemap
     * @param minLastMod     the minimal lastMod date that a sitemap entry or sitemap index entry must have to be
     *                       considered. The date must be equal or after the passed date
     * @return the sitemap from the passed URL containing only entries after the passed minLastMod date
     * @throws otherclasses.parser.model.InvalidSitemapUrlException when the sitemap from the passed inputStream is of type INDEX and one of the
     * referenced sitemaps could not be retrieved
     * @throws otherclasses.parser.model.SitemapParseException when the sitemap could not be parsed
     */
    public Sitemap parseSitemap(InputStream inputStream, URL url, Date minLastMod) {
        return parseSitemap(inputStream, url, true, minLastMod);
    }

    private Sitemap parseSitemap(String url, boolean recursive, Date minLastMod) {
        return parseSitemap(otherclasses.parser.HttpConnection.newUrl(url), recursive, minLastMod);
    }

    private Sitemap parseSitemap(URL url, boolean recursive, Date minLastMod) {
        try (otherclasses.parser.HttpConnection httpConnection = new otherclasses.parser.HttpConnection(url, userAgent, timeout, ignoreTlsCertificates)) {
            int responseCode = httpConnection.getResponseCode();
            if (responseCode < 200 || responseCode >= 300) {
                throw new InvalidSitemapUrlException("Sitemap URL " + url + " could not be loaded. HTTP status code "
                        + responseCode + " was returned.");
            }
            return parseSitemap(httpConnection.getInputStream(), url, recursive, minLastMod);
        }
    }

    private Sitemap parseSitemap(InputStream inputStream, URL url, boolean recursive, Date minLastMod) {
        otherclasses.parser.ISitemapParser sitemapParser = parseSitemapFlat(inputStream, url, minLastMod);
        if (sitemapParser.getSitemapType() == Sitemap.SitemapType.TEXT || sitemapParser.getSitemapType() == Sitemap.SitemapType.XML
                || (sitemapParser.getSitemapType() == Sitemap.SitemapType.INDEX && !recursive)) {
            return new Sitemap(sitemapParser.getSitemapIndexes(), sitemapParser.getSitemapEntries(), sitemapParser.getSitemapType());
        }
        Set<SitemapEntry> sitemapEntries = new LinkedHashSet<>();
        for (SitemapIndex sitemapIndex : sitemapParser.getSitemapIndexes()) {
            if (minLastMod != null && sitemapIndex.getLastMod() != null && sitemapIndex.getLastMod().before(minLastMod)) {
                continue;
            }
            URL sitemapUrl = otherclasses.parser.HttpConnection.newUrl(sitemapIndex.getLoc());
            try (otherclasses.parser.HttpConnection httpConnection = new otherclasses.parser.HttpConnection(sitemapUrl, userAgent, timeout, ignoreTlsCertificates)) {
                int responseCode = httpConnection.getResponseCode();
                if (responseCode < 200 || responseCode >= 300) {
                    throw new InvalidSitemapUrlException("Sitemap URL " + sitemapUrl + " could not be loaded. HTTP status code "
                            + responseCode + " was returned.");
                }
                sitemapParser = parseSitemapFlat(httpConnection.getInputStream(), sitemapUrl, minLastMod);
                if (sitemapParser.getSitemapType() == Sitemap.SitemapType.INDEX) {
                    throw new SitemapParseException("Sitemap index must not contain URLs to further sitemap indexes.");
                }
                sitemapEntries.addAll(sitemapParser.getSitemapEntries());
            }
        }
        return new Sitemap(sitemapParser.getSitemapIndexes(), sitemapEntries, Sitemap.SitemapType.INDEX);
    }

    private otherclasses.parser.ISitemapParser parseSitemapFlat(InputStream inputStream, URL url, Date minLastMod) {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        bufferedInputStream.mark(1024);
        String urlPrefix = getUrlPrefix(url);
        otherclasses.parser.ISitemapParser sitemapParser = new otherclasses.parser.XmlSitemapParser(bufferedInputStream, urlPrefix, continueOnErrors, minLastMod);
        try {
            sitemapParser.parse();
        } catch (otherclasses.parser.InvalidXmlException e) {
            sitemapParser = new otherclasses.parser.PlaintextSitemapParser(urlPrefix, bufferedInputStream);
            try {
                bufferedInputStream.reset();
            } catch (IOException e1) {
                throw new SitemapParseException("Error parsing sitemap: " + url);
            }
            sitemapParser.parse();
        }
        return sitemapParser;
    }

    private static String getUrlPrefix(URL url) {
        StringBuilder urlPrefixBuilder = new StringBuilder();
        urlPrefixBuilder.append(url.getProtocol()).append("://").append(url.getHost());
        if (url.getPort() != -1) {
            urlPrefixBuilder.append(":").append(url.getPort());
        }
        String urlPath = url.getPath();
        int lastIndexOfSlash = urlPath.lastIndexOf("/");
        if (lastIndexOfSlash != -1) {
            urlPath = urlPath.substring(0, lastIndexOfSlash + 1);
        }
        urlPrefixBuilder.append(urlPath);
        return urlPrefixBuilder.toString();
    }
}
