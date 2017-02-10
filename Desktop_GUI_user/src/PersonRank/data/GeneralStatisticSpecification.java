/*
 *  
 */
package PersonRank.data;

/**
 *
 */
public class GeneralStatisticSpecification {

    public static Specification<GeneralStatisticOnSite> getAllStatisticSite() {
        return new Specification<GeneralStatisticOnSite>() {
            @Override
            public boolean IsSatisfiedBy(GeneralStatisticOnSite entry) {
                return true;
            }
        };
    }

    public static Specification<GeneralStatisticOnSite> findStatisticSite(final String nameSite) {
        return new Specification<GeneralStatisticOnSite>() {
            @Override
            public boolean IsSatisfiedBy(GeneralStatisticOnSite entry) {
                return nameSite.equals(entry.getSiteName());
            }
        };
    }
}
