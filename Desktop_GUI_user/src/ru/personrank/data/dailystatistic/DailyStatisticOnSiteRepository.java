/*
 *  
 */
package ru.personrank.data.dailystatistic;

import ru.personrank.data.Repository;
import ru.personrank.data.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 
 */
public class DailyStatisticOnSiteRepository implements Repository<DailyStatisticOnSite> {

    private static final DailyStatisticOnSiteRepository INSTANCE = new DailyStatisticOnSiteRepository();

    ArrayList<DailyStatisticOnSite> dailyStatisticOnSite;

    private DailyStatisticOnSiteRepository() {
        dailyStatisticOnSite = new ArrayList<>();
        // Тестовые данные
        // Начало            
            DailyStatisticOnSite.Person p1 = new DailyStatisticOnSite.Person("Путин",
                    Arrays.asList(new Calendar[] {new GregorianCalendar(2017, Calendar.JANUARY, 17),
                                                    new GregorianCalendar(2017, Calendar.JANUARY, 19),
                                                    new GregorianCalendar(2017, Calendar.JANUARY, 21),
                                                    new GregorianCalendar(2017, Calendar.JANUARY, 22),
                                                    new GregorianCalendar(2017, Calendar.JANUARY, 23),
                                                    new GregorianCalendar(2017, Calendar.JANUARY, 24),
                                                    new GregorianCalendar(2017, Calendar.JANUARY, 25),
                                                    new GregorianCalendar(2017, Calendar.JANUARY, 26),
                                                    new GregorianCalendar(2017, Calendar.JANUARY, 27),
                                                    new GregorianCalendar(2017, Calendar.JANUARY, 29)}),
                    Arrays.asList(new Integer [] {4,2,1,3,5,8,6,7,1,9}));
            DailyStatisticOnSite.Person p2 = new DailyStatisticOnSite.Person("Трамп",
                    Arrays.asList(new Calendar[] {new GregorianCalendar(2017, Calendar.JANUARY, 18),
                                                    new GregorianCalendar(2017, Calendar.JANUARY, 20),
                                                    new GregorianCalendar(2017, Calendar.JANUARY, 21),
                                                    new GregorianCalendar(2017, Calendar.JANUARY, 22),
                                                    new GregorianCalendar(2017, Calendar.JANUARY, 23),
                                                    new GregorianCalendar(2017, Calendar.JANUARY, 24),
                                                    new GregorianCalendar(2017, Calendar.JANUARY, 25),
                                                    new GregorianCalendar(2017, Calendar.JANUARY, 26),
                                                    new GregorianCalendar(2017, Calendar.JANUARY, 27),
                                                    new GregorianCalendar(2017, Calendar.JANUARY, 28)}),
                    Arrays.asList(new Integer [] {2,1,3,7,5,9,7,2,1,4}));
            DailyStatisticOnSite site1 = new DailyStatisticOnSite("lenta.ru",Arrays.asList(new DailyStatisticOnSite.Person[] {p1,p2}));
            dailyStatisticOnSite.add(site1);
            
            DailyStatisticOnSite.Person p3 = new DailyStatisticOnSite.Person("Путин",
                    Arrays.asList(new Calendar[] {new GregorianCalendar(2017, Calendar.JANUARY, 17),
                                                    new GregorianCalendar(2017, Calendar.JANUARY, 20),
                                                    new GregorianCalendar(2017, Calendar.JANUARY, 21)}),
                    Arrays.asList(new Integer [] {1,4,2}));
            DailyStatisticOnSite.Person p4 = new DailyStatisticOnSite.Person("Трамп",
                    Arrays.asList(new Calendar[] {new GregorianCalendar(2017, Calendar.JANUARY, 19),
                                                    new GregorianCalendar(2017, Calendar.JANUARY, 20),
                                                    new GregorianCalendar(2017, Calendar.JANUARY, 21)}),
                    Arrays.asList(new Integer [] {5,2,1}));
            DailyStatisticOnSite.Person p5 = new DailyStatisticOnSite.Person("Обама",
                    Arrays.asList(new Calendar[] {new GregorianCalendar(2017, Calendar.JANUARY, 17),
                                                    new GregorianCalendar(2017, Calendar.JANUARY, 19),
                                                    new GregorianCalendar(2017, Calendar.JANUARY, 21)}),
                    Arrays.asList(new Integer [] {4,8,2}));
            DailyStatisticOnSite site2 = new DailyStatisticOnSite("komersant.ru",Arrays.asList(new DailyStatisticOnSite.Person[] {p3,p4,p5}));
            dailyStatisticOnSite.add(site2);
        // Конец
    }

    public static DailyStatisticOnSiteRepository getInstance() {
        return INSTANCE;
    }
    
    @Override
    public void add(DailyStatisticOnSite entry) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void remove(DailyStatisticOnSite entry) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void update(DailyStatisticOnSite entry) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public List<DailyStatisticOnSite> query(Specification specification) {
        List<DailyStatisticOnSite> newList = new ArrayList<>();
        for (DailyStatisticOnSite dsos : dailyStatisticOnSite) {
            if (specification.IsSatisfiedBy(dsos)) {
                newList.add(dsos);
            }
        }
        return newList; 
    }  
    
}
