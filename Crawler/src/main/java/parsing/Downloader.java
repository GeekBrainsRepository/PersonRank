package parsing;

import org.xml.sax.SAXException;
import parsing.parser.SitemapParser;
import parsing.parser.model.Sitemap;
import parsing.parser.model.SitemapEntry;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class Downloader {

    private static ArrayList<String> linksAtUrl = new ArrayList<>();
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";


    private ArrayList<String> download(String url) {
        //Поиск sitemap
        SitemapParser p = new SitemapParser();
        p.setUserAgent(USER_AGENT);
        Set<String> set = p.getSitemapLocations(url);
        //Парсим сайтмэп на наличие ссылок и добавляем их в коллекцию.
        for(String s1 : set){
            Sitemap sitemap = p.parseSitemap(s1, true);
            for(SitemapEntry links: sitemap.getSitemapEntries()){
               linksAtUrl.add(links.getLoc());
            }
        }
        return linksAtUrl;
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        Downloader d = new Downloader();
        d.download("http://bookflow.ru");
        for(String l: linksAtUrl){
            System.out.println(l);
        }
    }

}
