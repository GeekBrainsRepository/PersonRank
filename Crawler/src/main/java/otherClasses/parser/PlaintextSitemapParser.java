package otherclasses.parser;


import otherclasses.parser.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;


class PlaintextSitemapParser implements ISitemapParser {
    private static final Pattern URL_PATTERN = Pattern.compile("https?://[A-Za-z0-9\\-\\._~:/\\?#\\[\\]@!\\$&'\\(\\)\\*\\+,;=%]+");
    private static final int INVALID_LINES_ALLOWED = 3;
    private final String urlPrefix;
    private final InputStream inputStream;
    private Set<SitemapEntry> sitemapEntries;
    private boolean validUrlFound = false;

    public PlaintextSitemapParser(String urlPrefix, InputStream inputStream) {
        this.urlPrefix = urlPrefix;
        this.inputStream = inputStream;
    }

    @Override
    public void parse() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        sitemapEntries = new LinkedHashSet<>();
        String line;
        int lineCount = 0;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                lineCount++;
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                if (!validUrlFound) {
                    if (isUrl(line)) {
                        validUrlFound = true;
                    } else if (lineCount >= INVALID_LINES_ALLOWED) {
                        throw new SitemapParseException("Sitemap seems to be invalid. Please make sure that this is a valid sitemap.");
                    }
                }
                if (line.startsWith(urlPrefix)) {
                    sitemapEntries.add(new SitemapEntry(line));
                }
            }
        } catch (IOException e) {
            throw new UrlConnectionException(e.getMessage());
        }
    }

    private boolean isUrl(String text) {
        return URL_PATTERN.matcher(text).matches();
    }

    @Override
    public Set<SitemapIndex> getSitemapIndexes() {
        return new LinkedHashSet<>(0);
    }

    @Override
    public Set<SitemapEntry> getSitemapEntries() {
        return sitemapEntries;
    }

    @Override
    public Sitemap.SitemapType getSitemapType() {
        return Sitemap.SitemapType.TEXT;
    }
}
