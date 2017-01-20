/*
 * 
 */
package PersonRank.data;

import java.util.ArrayList;

/**
 *
 */
public class Site {
    
    private String name;
    private ArrayList<Page> pages; 

    public Site(String name, ArrayList<Page> pages) {
        this.name = name;
        this.pages = pages;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Page> getPages() {
        return pages;
    }
    
}
