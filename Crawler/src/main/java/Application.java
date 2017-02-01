import java.io.IOException;

/**
 * main class for run
 */
public class Application {
    public static void main(String[] args) throws IOException {
        PagesCreater pagesCreater = new PagesCreater();
        pagesCreater.parseForLinks("https://lenta.ru/");

        int i = 0;
        for(int j = 0 ; j < pagesCreater.getLinks().size()/2; j++){
            i += Parser.searchWord("Трамп", pagesCreater.getLinks().get(j));
        }
        System.out.println(i);//ну теперь более менее поделили, теперь подцепить надо будет бд
    }
}
