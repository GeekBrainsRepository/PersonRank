import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Parser {
    private static String getPlainText(String url){

        StringBuffer result = new StringBuffer();
        Document doc = null;
        try{
            doc = Jsoup.connect(url).get(); //todo рефактор
        } catch(IOException e){
            e.printStackTrace();
            System.out.println("Возникла ошибка при соединении: " + url);//todo логгер
        }

        String titles = doc.select("title").text();
        String paragraphText = doc.select("p").text();//todo добавить заголовки, ща
        String headerH1 = doc.select("h1").text();
        String headerH2 = doc.select("h2").text();
        String headerH3 = doc.select("h3").text();
        result.append(titles + "\n" + paragraphText + "\n" + headerH1 + "\n" + headerH2 + "\n" + headerH3);
        return result.toString();
    }

    public static int searchWord(String word, String url){
        int rank = 0;
        String regex = word + "*";
        String text = getPlainText(url);
        for(int i = 0 ; i < text.length(); i++){
            if(text.regionMatches(true,i,regex,0,word.length())){
                rank++;
            }
        }
        return rank;
    }
}
