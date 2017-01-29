package parsing.parser;

import parsing.parser.model.Sitemap;
import parsing.parser.model.SitemapEntry;
import parsing.parser.model.SitemapIndex;

import java.util.Set;

interface ISitemapParser {
    void parse();
    Set<SitemapIndex> getSitemapIndexes();
    Set<SitemapEntry> getSitemapEntries();
    Sitemap.SitemapType getSitemapType();
}
