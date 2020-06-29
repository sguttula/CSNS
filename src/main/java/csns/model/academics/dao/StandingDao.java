package csns.model.academics.dao;

import java.util.List;

import csns.model.academics.Standing;

public interface StandingDao {

    Standing getStanding( Long id );

    List<Standing> getStandings();

}
