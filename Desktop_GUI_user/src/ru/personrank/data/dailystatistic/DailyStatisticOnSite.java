/*
 *  
 */
package ru.personrank.data.dailystatistic;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 *
 */
public class DailyStatisticOnSite implements Serializable {

    private String siteName;
    private List<Person> persons;

    public DailyStatisticOnSite(String siteName, List persons) {
        this.siteName = siteName;
        this.persons = persons;
    }

    public String getSiteName() {
        return siteName;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public static class Person implements Serializable {
        private String name;
        private List<Calendar> scanDate;
        private List<Integer> numNewPages;

        public Person(String name, List<Calendar> scanDate, List<Integer> newPages) {
            this.name = name;
            this.scanDate = scanDate;
            this.numNewPages = newPages;
        }

        public String getName() {
            return name;
        }

        public List<Calendar> getScanDate() {
            return scanDate;
        }

        public List<Integer> getNewPages() {
            return numNewPages;
        }

    }

}
