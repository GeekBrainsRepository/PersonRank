/*
 * 
 */
package PersonRank.data;

import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 */
class Page {
   
    private String url;
    private Site site;
    private ArrayList<PersonPageRank> personPageRank;
    private Calendar foundDate;
    private Calendar lastScanDate;
    
    // !!!Заменить "шаблоном" длинный конструктор
    public Page(String url, Site site, ArrayList<PersonPageRank> personPageRank, Calendar foundDate, Calendar lastScanDate) {
        this.url = url;
        this.site = site;
        this.personPageRank = personPageRank;
        this.foundDate = foundDate;
        this.lastScanDate = lastScanDate;
    }

    public String getUrl() {
        return url;
    }

    public Site getSite() {
        return site;
    }

    public ArrayList<PersonPageRank> getPersonPageRank() {
        return personPageRank;
    }

    public Calendar getFoundDate() {
        return foundDate;
    }

    public Calendar getLastScanDate() {
        return lastScanDate;
    }

    
}
