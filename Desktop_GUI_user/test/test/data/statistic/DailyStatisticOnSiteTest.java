package test.data.statistic;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.personrank.data.dailystatistic.DailyStatisticOnSite;
import ru.personrank.data.dailystatistic.DailyStatisticOnSite.Person;
import static org.junit.Assert.*;

/**
 *
 * @author Мартынов Евгений
 */
public class DailyStatisticOnSiteTest {

    private DailyStatisticOnSite testSite1, testSite2, testSite3, testSite4;

    @Before
    public void setUp() {
        Person p1 = new Person("Персона1",
                Arrays.asList(new Calendar[]{
            new GregorianCalendar(2017, Calendar.JANUARY, 1),
            new GregorianCalendar(2017, Calendar.JANUARY, 2),
            new GregorianCalendar(2017, Calendar.JANUARY, 3),
            new GregorianCalendar(2017, Calendar.JANUARY, 4),
            new GregorianCalendar(2017, Calendar.JANUARY, 5),
            new GregorianCalendar(2017, Calendar.JANUARY, 6),
            new GregorianCalendar(2017, Calendar.JANUARY, 7),
            new GregorianCalendar(2017, Calendar.JANUARY, 8),
            new GregorianCalendar(2017, Calendar.JANUARY, 9),
            new GregorianCalendar(2017, Calendar.JANUARY, 10)}),
                Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0}));
        Person p2 = new Person("Персона2",
                Arrays.asList(new Calendar[]{
            new GregorianCalendar(2017, Calendar.JANUARY, 5),
            new GregorianCalendar(2017, Calendar.JANUARY, 6),
            new GregorianCalendar(2017, Calendar.JANUARY, 7),
            new GregorianCalendar(2017, Calendar.JANUARY, 8),
            new GregorianCalendar(2017, Calendar.JANUARY, 9),
            new GregorianCalendar(2017, Calendar.JANUARY, 10),
            new GregorianCalendar(2017, Calendar.JANUARY, 11),
            new GregorianCalendar(2017, Calendar.JANUARY, 12),
            new GregorianCalendar(2017, Calendar.JANUARY, 13),
            new GregorianCalendar(2017, Calendar.JANUARY, 14)}),
                Arrays.asList(new Integer[]{5, 6, 7, 8, 9, 10, 11, 12, 13, 14}));
        testSite1 = new DailyStatisticOnSite("Сайт1", Arrays.asList(new Person[]{p1, p2}));

        Person p3 = new Person("Персона2",
                Arrays.asList(new Calendar[]{
            new GregorianCalendar(2017, Calendar.JANUARY, 8),
            new GregorianCalendar(2017, Calendar.JANUARY, 9),
            new GregorianCalendar(2017, Calendar.JANUARY, 10)}),
                Arrays.asList(new Integer[]{8, 9, 10}));
        Person p4 = new Person("Персона3",
                Arrays.asList(new Calendar[]{
            new GregorianCalendar(2017, Calendar.JANUARY, 11),
            new GregorianCalendar(2017, Calendar.JANUARY, 12),
            new GregorianCalendar(2017, Calendar.JANUARY, 13)}),
                Arrays.asList(new Integer[]{11, 12, 13}));
        Person p5 = new Person("Персона4",
                Arrays.asList(new Calendar[]{
            new GregorianCalendar(2017, Calendar.JANUARY, 14),
            new GregorianCalendar(2017, Calendar.JANUARY, 15),
            new GregorianCalendar(2017, Calendar.JANUARY, 16)}),
                Arrays.asList(new Integer[]{14, 15, 16}));
        testSite2 = new DailyStatisticOnSite("Сайт2", Arrays.asList(new Person[]{p3, p4, p5}));
        testSite3 = new DailyStatisticOnSite("Сайт2", Arrays.asList(new Person[]{p3, p4, p5}));
        testSite4 = new DailyStatisticOnSite("Сайт2", Arrays.asList(new Person[]{p3, p4, p5}));
    }

    @After
    public void tearDown() {
        testSite1 = null;
        testSite2 = null;
        testSite3 = null;
        testSite4 = null;
    }

    /**
     * Проверяет метод equals на соответствие общим соглашениям.
     */
    @Test
    public void testEquals() {
        if (testSite2.equals(testSite2)) {
            if (testSite2.equals(testSite3) && testSite3.equals(testSite2)) {
                if (testSite2.equals(testSite3)
                        && testSite2.equals(testSite4)
                        && testSite3.equals(testSite4)) {
                    if (!testSite2.equals(null)) {
                        if (!testSite1.equals(testSite2)) {
                            assertTrue(true);
                        } else {
                            fail("Не пройден тест на сравнение заведомо"
                                    + "не равных обьектов");
                        }
                    } else {
                        fail("Не пройден тест на неравенство null!");
                    }
                } else {
                    fail("Не пройден тест на транзистивность!");
                }
            } else {
                fail("Не пройден тест на симетричность!");
            }
        } else {
            fail("Не пройден тест на рефлективность!");
        }
    }

}
