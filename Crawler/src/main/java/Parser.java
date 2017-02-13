import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class Parser {

    private static String getPlainText(String url){
        StringBuffer result = new StringBuffer();
        Document document = null;
        try{
            document = Jsoup.connect(url).get(); //todo рефактор
        } catch (SocketTimeoutException socketTimeoutException) {
            System.out.println("timeout read");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch(IOException e){
            e.printStackTrace();
            System.out.println("Возникла ошибка при соединении: " + url);//todo логгер
        }

        String titles = document.select("title").text();
        String paragraphText = document.select("p").text();
        String headerH1 = document.select("h1").text();
        String headerH2 = document.select("h2").text();
        String headerH3 = document.select("h3").text();
        result.append(titles + "\n" + paragraphText + "\n" + headerH1 + "\n" + headerH2 + "\n" + headerH3);
        return result.toString();
    }

    //Поиск как нескольких слов связанных с персоной, так и только самой персоны
    public static int searchWord(String phrase, String url){
        int rank = 0;
        String text = getPlainText(url);
        //Проверка на количество слов в поисковом запросе
        if(phrase.contains(" ")){
                for(int i = 0 ; i < text.length(); i++){
                    if(text.regionMatches(true,i,phrase,0,phrase.length())){
                        rank++;
                    }
                }
        }
        String regex = phrase + "*";
        for(int i = 0 ; i < text.length(); i++){
            if(text.regionMatches(true,i,regex,0,phrase.length())){
                rank++;
            }
        }
        return rank;
    }
}
