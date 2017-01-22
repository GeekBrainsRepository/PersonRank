package beans;

import java.util.Calendar;

/**
 * бин для таблицы Pages
 */
public class Pages {

    private int id;
    private String url;
    private int siteId;
    private Calendar foundDateTime;
    private Calendar lastScanDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public Calendar getFoundDateTime() {
        return foundDateTime;
    }

    public void setFoundDateTime(Calendar foundDateTime) {
        this.foundDateTime = foundDateTime;
    }

    public Calendar getLastScanDate() {
        return lastScanDate;
    }

    public void setLastScanDate(Calendar lastScanDate) {
        this.lastScanDate = lastScanDate;
    }
}
