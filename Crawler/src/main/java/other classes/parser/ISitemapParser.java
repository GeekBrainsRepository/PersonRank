package geekbrains.internship.project_full.Crawler.src.main.java.other_classes.parser;

import geekbrains.internship.project_full.Crawler.src.main.java.other_classes.parser.model.Sitemap;
import geekbrains.internship.project_full.Crawler.src.main.java.other_classes.parser.model.SitemapEntry;
import geekbrains.internship.project_full.Crawler.src.main.java.other_classes.parser.model.SitemapIndex;

import java.util.Set;

interface ISitemapParser {
    void parse();
    Set<SitemapIndex> getSitemapIndexes();
    Set<SitemapEntry> getSitemapEntries();
    Sitemap.SitemapType getSitemapType();
}
