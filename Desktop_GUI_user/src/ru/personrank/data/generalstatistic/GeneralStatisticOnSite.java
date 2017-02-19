
package ru.personrank.data.generalstatistic;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * Используется для хранения данных таблицы "Общая статистика"
 * 
 * @author Мартынов Евгений
 */
public class GeneralStatisticOnSite implements Serializable {

    private String siteName;
    private Calendar reviewDate;
    private List<String> personNames;
    private List<Integer> allPersonRanks;

    /**
     * Создает объект данных таблицы с названием сайта, датой последнего
     * обновления статистики, списком личностей и списком рейтинга.
     * 
     * @param siteName - имя сайта
     * @param reviewDate - дата обновления статистики
     * @param personNames - список личностей в виде коллекции List
     * @param allPersonRanks - список рейтингов в виде коллекции List
     */
    public GeneralStatisticOnSite(String siteName, Calendar reviewDate, List<String> personNames, List<Integer> allPersonRanks) {
        this.siteName = siteName;
        this.reviewDate = reviewDate;
        this.personNames = personNames;
        this.allPersonRanks = allPersonRanks;
    }

    /**
     * Возвращает имя сайта. 
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * Возвращает дату последнего обновления статистики. 
     */
    public Calendar getReviewDate() {
        return reviewDate;
    }

    /**
     * Возвращает список личностей. 
     */
    public List<String> getPersonNames() {
        return personNames;
    }

    /**
     * Возвращает список рейтингов. 
     */
    public List<Integer> getAllPersonRanks() {
        return allPersonRanks;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = (siteName == null) ? result + 0 : 31 * result + siteName.hashCode();
        result = (reviewDate == null) ? result + 0 : 31 * result + reviewDate.hashCode();
        result = (personNames == null) ? result + 0 : 31 * result + personNames.hashCode();
        result = (allPersonRanks == null) ? result + 0 : result + allPersonRanks.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GeneralStatisticOnSite)) {
            return false;
        }
        final GeneralStatisticOnSite other = (GeneralStatisticOnSite) obj;
        if (!Objects.equals(this.siteName, other.siteName)) {
            return false;
        }
        if (!Objects.equals(this.reviewDate, other.reviewDate)) {
            return false;
        }
        if (!Objects.equals(this.personNames, other.personNames)) {
            return false;
        }
        if (!Objects.equals(this.allPersonRanks, other.allPersonRanks)) {
            return false;
        }
        return true;
    }

}
