import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Downloader {
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private List<String> links = new LinkedList<String>();
    private Document htmlDocument;

    public List<String> download(String url){
        try{

            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;

            if(connection.response().statusCode() == 200){

                System.out.println("\n**Посещаю** Полученную веб-страницу на " + url);
            }
            if(!connection.response().contentType().contains("text/html")){

                System.out.println("**Ошибка** Тип страницы отличается от HTML");
            }

            Elements linksOnPage = htmlDocument.select("a[href]");
            System.out.println("Найдено (" + linksOnPage.size() + ") ссылок");

            for(Element link : linksOnPage){

                this.links.add(link.absUrl("href"));

            }


        }
        catch(IOException ioe){

           ioe.printStackTrace();
        }
        return links;
     }

    }
}
