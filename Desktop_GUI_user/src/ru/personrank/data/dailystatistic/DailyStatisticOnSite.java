
package ru.personrank.data.dailystatistic;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * Используется для хранения данных для таблицы "Ежедневная статистика"
 * 
 * @author Мартынов Евгений
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

    @Override
    public int hashCode() {
        int result = 17;
        result = (siteName == null) ? result + 0 : 31 * result + siteName.hashCode();
        result = (persons == null) ? result + 0 : 31 * result + persons.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DailyStatisticOnSite)) {
            return false;
        }
        final DailyStatisticOnSite other = (DailyStatisticOnSite) obj;
        if (!Objects.equals(this.siteName, other.siteName)) {
            return false;
        }
        if (!Objects.equals(this.persons, other.persons)) {
            return false;
        }
        return true;
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

        @Override
        public int hashCode() {
            int result = 17;
            result = (name == null) ? result + 0 : 31 * result + name.hashCode();
            result = (scanDate == null) ? result + 0 : 31 * result + scanDate.hashCode();
            result = (numNewPages == null) ? result + 0 : 31 * result + numNewPages.hashCode();
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Person)) {
                return false;
            }
            final Person other = (Person) obj;
            if (!Objects.equals(this.name, other.name)) {
                return false;
            }
            if (!Objects.equals(this.scanDate, other.scanDate)) {
                return false;
            }
            if (!Objects.equals(this.numNewPages, other.numNewPages)) {
                return false;
            }
            return true;
        }

    }
    
}
