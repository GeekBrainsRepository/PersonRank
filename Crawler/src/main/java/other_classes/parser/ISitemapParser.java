package other_classes.parser;


import other_classes.parser.model.Sitemap;
import other_classes.parser.model.SitemapEntry;
import other_classes.parser.model.SitemapIndex;

import java.util.Set;

interface ISitemapParser {
    void parse();
    Set<SitemapIndex> getSitemapIndexes();
    Set<SitemapEntry> getSitemapEntries();
    Sitemap.SitemapType getSitemapType();
}
