package csns.model.wiki.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import csns.helper.WikiSearchResult;

@Test(groups = "PageDaoTests")
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
public class PageDaoTests extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    PageDao pageDao;

    public void searchPages()
    {
        List<WikiSearchResult> results = pageDao.searchPages( "wiki", 10 );
        assert results.size() == 1;
    }

}
