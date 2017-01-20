/*
 * 
 */
package PersonRank.data;

import java.util.ArrayList;

/**
 *
 *
 */
public class Person {

    private String name;
    private ArrayList<Keyword> keywords;

    public Person(String name, ArrayList<Keyword> keywords) {
        this.name = name;
        this.keywords = keywords;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Keyword> getKeywords() {
        return keywords;
    }
   
}
