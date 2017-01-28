package parsing.parser;

import otherclasses.parser.model.Sitemap;
import otherclasses.parser.model.SitemapEntry;
import otherclasses.parser.model.SitemapIndex;

import java.util.Set;

interface ISitemapParser {
    void parse();
    Set<SitemapIndex> getSitemapIndexes();
    Set<SitemapEntry> getSitemapEntries();
    Sitemap.SitemapType getSitemapType();
}
