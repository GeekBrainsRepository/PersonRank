package other_classes;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HtmlParser {
    //@return Текст со страницы
    public  static  synchronized String parseHtml(String pageUrl) throws IOException {

        URL url = new URL(pageUrl);
        URLConnection uc = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        StringBuffer inputLine = new StringBuffer();
        String s;
        while ((s = in.readLine())!= null){
            inputLine.append(s + "\n");
        }
        in.close();

        return inputLine.toString();
    }

    //@return Количество раз, которое слово встречается на странице.
    //@param html - адрес строницы, которую нужно пропарсить.
    //@param word - слово, которе ищем.
    public static int countWord(String html, String word) throws IOException {
        String reg = word + "*";
        int count = 0;
        String h = parseHtml(html);
        Document doc = Jsoup.parse(h);
        Elements node = doc.body().getElementsMatchingText(reg);
        for(Element e: node){
            count++;
        }
        return count;
    }
}
