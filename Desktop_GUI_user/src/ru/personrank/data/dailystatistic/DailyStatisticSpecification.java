/*
 * 
 */
package ru.personrank.data.dailystatistic;

import ru.personrank.data.Specification;

/**
 *
 */
public class DailyStatisticSpecification {

    private DailyStatisticSpecification() {}
    
    public static Specification<DailyStatisticOnSite> getAllStatisticSite() {
        return new Specification<DailyStatisticOnSite>() {
            @Override
            public boolean IsSatisfiedBy(DailyStatisticOnSite entry) {
                return true;
            }
        };
    }

    public static Specification<DailyStatisticOnSite> findStatisticSite(final String nameSite) {
        return new Specification<DailyStatisticOnSite>() {
            @Override
            public boolean IsSatisfiedBy(DailyStatisticOnSite entry) {
                return nameSite.equals(entry.getSiteName());
            }
        };
    }
}
