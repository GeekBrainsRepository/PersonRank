
package ru.personrank.data.dailystatistic;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * Используется для хранения данных таблицы "Ежедневная статистика"
 * 
 * @author Мартынов Евгений
 */
public class DailyStatisticOnSite implements Serializable {

    private String siteName;
    private List<Person> persons;

    /**
     * Создает обьект данных таблицы с названием сайта и списком личностей.
     * 
     * @param siteName - название сайта
     * @param persons - список личностей в виде коллекции List
     */
    public DailyStatisticOnSite(String siteName, List persons) {
        this.siteName = siteName;
        this.persons = persons;
    }

    /**
     * Возвращает название сайта.
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * Возвращает список личностей.
     */
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

    /**
     * Внутренний класс использующийся для хранения информации о личностях.
     */
    public static class Person implements Serializable {

        private String name;
        private List<Calendar> scanDate;
        private List<Integer> numNewPages;

        /**
         * Создает обьект.
         * 
         * @param name - имя персоны
         * @param scanDate - даты в виде коллекции List 
         * @param newPages - количество страциц в виде коллекции List 
         */
        public Person(String name, List<Calendar> scanDate, List<Integer> newPages) {
            this.name = name;
            this.scanDate = scanDate;
            this.numNewPages = newPages;
        }

        /*
        * Возвращает имя персоны.
        */
        public String getName() {
            return name;
        }
        
        /**
         *  Возвращает список дат.
         */
        public List<Calendar> getScanDate() {
            return scanDate;
        }

        /**
         * Возвращает список количества страниц. 
         */
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
