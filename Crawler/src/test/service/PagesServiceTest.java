import beans.Pages;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.PagesService;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by Виктор on 01.02.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/mainTestContext.xml")
public class PagesServiceTest {
    @Autowired
    private PagesService pagesService;

    @Test
    public void insertPage() {
        Pages pages = new Pages();
        pages.setUrl("kuku.ru/robots.txt");
        pages.setSiteId(1);
        pages.setFoundDateTime(new Date(Calendar.getInstance().getTime().getTime()));
        pages.setLastScanDate(null);
        pagesService.insertPage(pages);
    }
}
