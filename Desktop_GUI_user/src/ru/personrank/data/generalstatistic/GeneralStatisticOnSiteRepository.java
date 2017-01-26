/*
 *  
 */
package ru.personrank.data.generalstatistic;

import ru.personrank.data.Repository;
import ru.personrank.data.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class GeneralStatisticOnSiteRepository implements Repository<GeneralStatisticOnSite> {

    private static final GeneralStatisticOnSiteRepository INSTANCE = new GeneralStatisticOnSiteRepository();

    ArrayList<GeneralStatisticOnSite> generalStatisticOnSite;

    private GeneralStatisticOnSiteRepository() {
        generalStatisticOnSite = new ArrayList<>();
        // Тестовые данные
        // Начало
        String siteName1 = "lenta.ru";
        ArrayList<String> persons1 = new ArrayList<>();
        persons1.add("Путин");
        persons1.add("Трамп");
        persons1.add("Обама");
        persons1.add("Меркиль");
        persons1.add("Оланд");
        persons1.add("Навальный");
        persons1.add("Жириновский");
        ArrayList<Integer> allRanks1 = new ArrayList<>();
        allRanks1.add(1227);
        allRanks1.add(1820);
        allRanks1.add(987);
        allRanks1.add(681);
        allRanks1.add(5787);
        allRanks1.add(57);
        allRanks1.add(121);
        generalStatisticOnSite.add(new GeneralStatisticOnSite(siteName1, persons1, allRanks1));

        String siteName2 = "komersant.ru";
        ArrayList<String> persons2 = new ArrayList<>();
        persons2.add("Путин");
        persons2.add("Трамп");
        persons2.add("Обама");
        persons2.add("Меркиль");
        persons2.add("Оланд");
        persons2.add("Навальный");
        persons2.add("Жириновский");
        ArrayList<Integer> allRanks2 = new ArrayList<>();
        allRanks2.add(1147);
        allRanks2.add(1745);
        allRanks2.add(1001);
        allRanks2.add(791);
        allRanks2.add(670);
        allRanks2.add(100);
        allRanks2.add(189);
        generalStatisticOnSite.add(new GeneralStatisticOnSite(siteName2, persons2, allRanks2));
        // Конец
    }

    public static GeneralStatisticOnSiteRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public void add(GeneralStatisticOnSite entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void remove(GeneralStatisticOnSite entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(GeneralStatisticOnSite entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<GeneralStatisticOnSite> query(Specification specification) {
        List<GeneralStatisticOnSite> newList = new ArrayList<>();
        for (GeneralStatisticOnSite gsos : generalStatisticOnSite) {
            if (specification.IsSatisfiedBy(gsos)) {
                newList.add(gsos);
            }
        }
        return newList;
    }

}