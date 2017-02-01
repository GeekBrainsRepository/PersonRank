import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class PagesCreater {
    private static ArrayList<String> links = new ArrayList<>(); // у нас уже есть лист
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private Document htmlDocument;
    private static Elements linksOnPage;

    public synchronized ArrayList<String> parseForLinks(String url) throws IOException {
        try{
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);// todo сделать try с ресурсами, будет проще
            htmlDocument = connection.get();
            if(connection.response().statusCode() == 200){
                System.out.println("\n Посещаем страницу: " + url);
            }
            if(! connection.response().contentType().contains("text/html")){
                System.out.println("Тип документа не является HTML");//todo логгер log4j
            }
            linksOnPage = htmlDocument.select("a[href]");

            System.out.println("Найдено (" + linksOnPage.size() + ") ссылок");
            for(Element link : linksOnPage){ //это что у нас
                this.links.add(link.absUrl("href"));
                print(" * a: <%s> (%s)", link.attr("abs:href"), trim(link.text(), 35)); //норм не сломал )
            }
            return links;
        } catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String> getLinks() {
        return links;
    }

    private static void print(String msg,Object...args){
        System.out.println(String.format(msg,args)); //
    }

    private static String trim(String s, int width){
        if(s.length() > width){ //комменты лучше оставлять ) а то можно забыть
            return s.substring(0,width - 1) + ".";
        } else {
            return s;
        }
    }
}
