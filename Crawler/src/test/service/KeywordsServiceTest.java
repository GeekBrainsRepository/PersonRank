import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.KeywordsService;

/**
 * Created by Виктор on 29.01.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/mainTestContext.xml")
public class KeywordsServiceTest {

    @Autowired
    private KeywordsService keywordsService;

    @Test
    public void getKeywords() {
        Assert.assertNotNull("getKeywords it's OK", keywordsService.getKeywords());
    }
}
