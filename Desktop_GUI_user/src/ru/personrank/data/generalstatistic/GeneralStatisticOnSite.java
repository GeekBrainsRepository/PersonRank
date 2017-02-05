/*
 *  
 */
package ru.personrank.data.generalstatistic;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 
 */
public class GeneralStatisticOnSite {
    private String siteName;
    private Calendar reviewDate;
    private List<String> personNames;
    private List<Integer> allPersonRanks;

    public GeneralStatisticOnSite(String siteName,Calendar reviewDate, List<String> personNames, List<Integer> allPersonRanks) {
        this.siteName = siteName;
        this.reviewDate = reviewDate;
        this.personNames = personNames;
        this.allPersonRanks = allPersonRanks;
    }

    public String getSiteName() {
        return siteName;
    }

    public Calendar getReviewDate() {
        return reviewDate;
    }
    
    public List<String> getPersonNames() {
        return personNames;
    }

    public List<Integer> getAllPersonRanks() {
        return allPersonRanks;
    }
    
    
}
