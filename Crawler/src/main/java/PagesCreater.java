import beans.Pages;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import services.PagesService;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@ContextConfiguration(locations = "/mainContext.xml")
public class PagesCreater {
    @Autowired
    private static PagesService pagesService;

    private static Pages pages = new Pages();
    private static ArrayList<String> links = new ArrayList<>();

    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private Document htmlDocument;
    private static Elements linksOnPage;
    private static final Logger log = Logger.getLogger(PagesCreater.class);
    private String contentType = null;
    private static Set<String> linksFromSitemaps = new HashSet<>();
    private static ArrayList<String> sitemapLinks = new ArrayList<>();

    public synchronized void parseForLinks(String url, int siteId)  {
        ApplicationContext context = new ClassPathXmlApplicationContext("mainContext.xml");
        pagesService = (PagesService) context.getBean("pagesService");

        try {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            htmlDocument = connection.get();

            //todo обработать все возможные расширения согласно спецификации
            // в том числе xml
//            if(!connection.response().contentType().contains(contentType)){
//                switch(contentType) {
//                    case "text/html":
//                        log.error("Тип документа не является HTML");
//                        break;
//                    case "text/xml":
//                        log.error("Тип документа не является XML");
//                        break;
//                    case  "application/xml":
//                        log.error("Тип документа не является XML");
//                        break;
//
//                    default: break;
//                }
//
//
//            }

            linksOnPage = htmlDocument.select("a[href]");

            log.info("Найдено (" + linksOnPage.size() + ") ссылок");

            for(Element link : linksOnPage){
//            //  todo Проверка на идентичные ссылки
                String pageIsChecked = link.absUrl("href");
                for(Pages page : pagesService.getPages()){
//                    Проверка статуса страницы, чтобы исключить добавление "сломанных ссылок".
                    if(Jsoup.connect(page.getUrl()).execute().statusCode() == 200){
                        //Исключаем дубликаты в pages.
                        if(! pageIsChecked.equals(pages.getUrl())){
                            pages.setSiteId(siteId);
                            pages.setUrl(link.absUrl("href"));
                            pages.setFoundDateTime(new Date(Calendar.getInstance().getTime().getTime()));
                            pagesService.insertPage(pages);
                            links.add(link.absUrl("href"));
//////                            print(" * a: <%s> (%s)", link.attr("abs:href"), trim(link.text(), 35));
                        }
                    }
                }
            }
            //Парсинг сайтмэпа
            for(Pages page: pagesService.getPages()){
                String pageIsChecked = page.getUrl();
                if(pageIsChecked.contains("robots.txt")){
                    getSitemapLinks(pageIsChecked);
                    for(String sitemapLink: sitemapLinks){
                        if(!connection.response().contentType().contains("application/octet-stream"))
                        getLinksFromSitemaps(sitemapLink);
                    }
                }
            }
            //Проверка ссылок из сайтмэпа и добавление их в качестве страниц
            for(Pages page:pagesService.getPages()){
                String pageIsChecked = page.getUrl();
                for(String linkFromSitemap : linksFromSitemaps){
                    if(!linkFromSitemap.equals(pageIsChecked)){
                        pages.setSiteId(siteId);
                        pages.setUrl(linkFromSitemap);
                        pages.setFoundDateTime(new Date(Calendar.getInstance().getTime().getTime()));
                        pagesService.insertPage(pages);
                        links.add(linkFromSitemap);
                    }else{
                        System.out.println("Such link exists " + linkFromSitemap + "!!!");
                    }
                }
            }
        } catch(IOException e){
            e.printStackTrace();
                log.error("Ошибка при посещении адреса " + url);
        }
    }

    public static ArrayList<String> getLinks() {
        return links;
    }

    private static void print(String msg,Object...args){
        System.out.println(String.format(msg,args));
    }

    private static String trim(String s, int width){
        if(s.length() > width){ //комменты лучше оставлять ) а то можно забыть
            return s.substring(0,width - 1) + ".";
        } else {
            return s;
        }
    }

    //возвращает ссылки на сайтмэпы с robots.txt в виде листа

    public static void getSitemapLinks(String link) throws IOException{
        //Вытаскивает ссылку с роботс тхт
        Document doc = Jsoup.connect(link).get();
        Elements el = doc.getElementsContainingOwnText("Sitemap");
        String[] ss = el.text().split(" ");
        for(String g:ss){
            if(g.contains("sitemap")){

                sitemapLinks.add(g);
            }
        }
    }

    //вытаскивает рекурсивно ссылки из сайтмэпов и записывает их в коллекцию Set

    public static void getLinksFromSitemaps(String sitemapLink) throws IOException {
        Document doc = Jsoup.connect(sitemapLink).get();
        Elements els = doc.getElementsContainingOwnText("http");
        String [] links = els.text().split(" ");
        for(String l: links){
            if(l.contains("sitemap")){
                getLinksFromSitemaps(l);
            } else{
                linksFromSitemaps.add(l);
            }
        }
    }

}
