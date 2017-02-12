import beans.Keywords;
import beans.Pages;
import beans.PersonPageRank;
import beans.Sites;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import services.KeywordsService;
import services.PagesService;
import services.PersonPageRankService;
import services.SitesService;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

/**
 * main class for run
 */
@ContextConfiguration(locations = "/mainContext.xml")
public class Application {
    private static final String USER_AGENT =
            "Chrome";
    @Autowired
    private static SitesService sitesService;
    @Autowired
    private static KeywordsService keywordsService;
    @Autowired
    private static PersonPageRankService personPageRankService;
    @Autowired
    private static PagesService pagesService;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("mainContext.xml");
        sitesService = (SitesService) context.getBean("sitesService");
        keywordsService = (KeywordsService) context.getBean("keywordsService");
        personPageRankService = (PersonPageRankService) context.getBean("personPageRankService");
        pagesService = (PagesService) context.getBean("pagesService");

        List<Sites> sites = sitesService.getSites();
        List<Keywords> keywordsList = keywordsService.getKeywords();
        List<Pages> pagesList = pagesService.getPages();

        PagesCreater pagesCreater = new PagesCreater();
        PersonPageRank personPageRank = new PersonPageRank();

        //for (Sites site : sites) {
        //    pagesCreater.parseForLinks(site.getName(),site.getId());
        //}

        for (Pages page : pagesList) {
            for (Keywords keyword : keywordsList) {
                try {
                    if (Jsoup.connect(page.getUrl()).execute().statusCode() == 200) { //todo проверка на доступность страницы, вылетает
                        personPageRank.setPersonId(keyword.getPersonId());
                        personPageRank.setPageId(page.getId());
                        personPageRank.setRank(Parser.searchWord(keyword.getName(), page.getUrl()));
                        personPageRankService.setInsertPersonPageRank(personPageRank);
                        System.out.println(personPageRank.toString());
                        pagesService.setUpdateLastScanDate(page.getId(), new Date(Calendar.getInstance().getTime().getTime()));
                    }
                } catch (UnknownHostException u) {
                    System.out.println("host problem -" + page.getUrl());
                } catch (IOException e) {
                    System.out.println("connect problem - " + page.getUrl() + keyword.getName());
                }
            }
        }
    }
}
