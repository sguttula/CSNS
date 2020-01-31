package csns.model.academics.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import csns.model.academics.Standing;
import csns.model.academics.dao.StandingDao;

@Repository
public class StandingDaoImpl implements StandingDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Standing getStanding( Long id )
    {
        return entityManager.find( Standing.class, id );
    }

    @Override
    public List<Standing> getStandings()
    {
        return entityManager.createQuery( "from Standing order by id asc",
            Standing.class ).getResultList();
    }

}
