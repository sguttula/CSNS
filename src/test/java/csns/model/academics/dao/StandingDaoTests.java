package csns.model.academics.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import csns.model.academics.Standing;

@Test(groups = "StandingDaoTests")
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
public class StandingDaoTests extends AbstractTestNGSpringContextTests {

    @Autowired
    StandingDao standingDao;

    @Test
    public void getStandings()
    {
        List<Standing> standings = standingDao.getStandings();

        assert standings.size() == 11;
        assert standings.get( 0 ).getSymbol().equals( "B" );
    }

}
