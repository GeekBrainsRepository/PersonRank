import beans.Keywords;
import beans.Pages;
import beans.PersonPageRank;
import beans.Sites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import services.KeywordsService;
import services.PagesService;
import services.PersonPageRankService;
import services.SitesService;

import java.io.IOException;
import java.util.List;

/**
 * main class for run
 */
@ContextConfiguration(locations = "/mainContext.xml")
public class Application {
    @Autowired
    private static SitesService sitesService;
    private static KeywordsService keywordsService;
    private static PersonPageRankService personPageRankService;
    private static PagesService pagesService;

    public static void main(String[] args) throws IOException {
        List<Sites> sites = sitesService.getSites();
        List<Keywords> keywordsList = keywordsService.getKeywords();
        List<Pages> pagesList = pagesService.getPages();

        PagesCreater pagesCreater = new PagesCreater();
        PersonPageRank personPageRank = new PersonPageRank();

        for (Sites site : sites) {
            pagesCreater.parseForLinks(site.getName(),site.getId());
        }
        //pagesCreater.parseForLinks("https://lenta.ru/");

        int i = 0;
        for(int j = 0 ; j < pagesCreater.getLinks().size()/2; j++){
            i += Parser.searchWord("Трамп", pagesCreater.getLinks().get(j));
        }
        System.out.println(i);
        for (Pages page : pagesList) {
            for (Keywords keyword : keywordsList) {
                personPageRank.setPageId(page.getId());
                personPageRank.setRank(Parser.searchWord(keyword.getName(),page.getUrl()));
                personPageRankService.setInsertPersonPageRank(personPageRank);
            }
        }
    }
}
