import services.KeywordsService;

/**
 * main class for run
 */
public class Main {
    public static void main(String[] args) {
        KeywordsService keywordsService = new KeywordsService();
        System.out.println(keywordsService.getKeywords());
    }
}
