/*
 *  
 */
package ru.personrank.data.generalstatistic;

import java.util.List;

/**
 * 
 */
public class GeneralStatisticOnSite {
    private String siteName;
    private List<String> personNames;
    private List<Integer> allPersonRanks;

    public GeneralStatisticOnSite(String siteName, List<String> personNames, List<Integer> allPersonRanks) {
        this.siteName = siteName;
        this.personNames = personNames;
        this.allPersonRanks = allPersonRanks;
    }

    public String getSiteName() {
        return siteName;
    }

    public List<String> getPersonNames() {
        return personNames;
    }

    public List<Integer> getAllPersonRanks() {
        return allPersonRanks;
    }
    
    
}
