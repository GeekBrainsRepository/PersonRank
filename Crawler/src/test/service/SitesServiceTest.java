import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.SitesService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/mainTestContext.xml")
public class SitesServiceTest {
    private SitesService sitesService;

    @Test
    public void getSites(){
        Assert.assertNotNull("Sites is not null",sitesService.getSites());}
}
