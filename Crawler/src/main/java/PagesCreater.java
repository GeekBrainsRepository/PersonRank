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

    public synchronized void parseForLinks(String url, int siteId)  {
        ApplicationContext context = new ClassPathXmlApplicationContext("mainContext.xml");
        pagesService = (PagesService) context.getBean("pagesService");
            try{
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);// todo сделать try с ресурсами, будет проще
            htmlDocument = connection.get();
            if(connection.response().statusCode() == 200){
                log.info("\n Посещаем страницу: " + url);
            }
            if(! connection.response().contentType().contains("text/html")){
                log.error("Тип документа не является HTML");
            }
            linksOnPage = htmlDocument.select("a[href]");

            log.info("Найдено (" + linksOnPage.size() + ") ссылок");
            for(Element link : linksOnPage){
                pages.setSiteId(siteId);
                pages.setUrl(link.absUrl("href"));
                pages.setFoundDateTime(new Date(Calendar.getInstance().getTime().getTime()));
                pagesService.insertPage(pages);
                this.links.add(link.absUrl("href"));
                print(" * a: <%s> (%s)", link.attr("abs:href"), trim(link.text(), 35));
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
}
