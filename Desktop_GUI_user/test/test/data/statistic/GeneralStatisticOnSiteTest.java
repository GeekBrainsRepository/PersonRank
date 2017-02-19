
package test.data.statistic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import ru.personrank.data.generalstatistic.GeneralStatisticOnSite;

/**
 *
 * @author Мартынов Евгений
 */
public class GeneralStatisticOnSiteTest {
    
    private GeneralStatisticOnSite testSite1, testSite2, testSite3, testSite4;
    
    @Before
    public void setUp() {
        
        String siteName1 = "Сайт1";
        Calendar data1 = new GregorianCalendar(2017, Calendar.JANUARY, 1);
        ArrayList<String> persons1 = new ArrayList<>();
        persons1.add("Персона1");
        persons1.add("Персона2");
        persons1.add("Персона3");
        ArrayList<Integer> ranks1 = new ArrayList<>();
        ranks1.add(1000);
        ranks1.add(2000);
        ranks1.add(3000);
        testSite1 = new GeneralStatisticOnSite(siteName1, data1, persons1, ranks1);
        
        String siteName2 = "Сайт2";
        Calendar data2 = new GregorianCalendar(2017, Calendar.JANUARY, 2);
        ArrayList<String> persons2 = new ArrayList<>();
        persons2.add("Персона4");
        persons2.add("Персона5");
        persons2.add("Персона6");
        ArrayList<Integer> ranks2 = new ArrayList<>();
        ranks2.add(4000);
        ranks2.add(5000);
        ranks2.add(6000);
        testSite2 = new GeneralStatisticOnSite(siteName2, data2, persons2, ranks2);
        testSite3 = new GeneralStatisticOnSite(siteName2, data2, persons2, ranks2);
        testSite4 = new GeneralStatisticOnSite(siteName2, data2, persons2, ranks2);
        
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
