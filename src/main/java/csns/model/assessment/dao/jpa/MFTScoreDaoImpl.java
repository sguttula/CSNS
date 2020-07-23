package csns.model.assessment.dao.jpa;

import java.util.Date;
import java.util.List;
 
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.academics.Department;
import csns.model.assessment.MFTScore;
import csns.model.assessment.dao.MFTScoreDao;
import csns.model.core.User; 

@Repository
public class MFTScoreDaoImpl implements MFTScoreDao {

    @PersistenceContext 
    private EntityManager entityManager;

    @Override
    public List<Date> getDates( Department department )
    {
        String query = "select distinct date from MFTScore "
            + " where department = :department order by date desc";

        return entityManager.createQuery( query, Date.class )
            .setParameter( "department", department )
            .getResultList();
    }

    @Override
    public List<Integer> getYears( Department department )
    {
        String query = "select distinct year(date) from MFTScore "
            + "where department = :department order by year(date) asc";

        return entityManager.createQuery( query, Integer.class )
            .setParameter( "department", department )
            .getResultList();
    }

    @Override
    public MFTScore getScore( Department department, Date date, User user )
    {
        String query = "from MFTScore where department = :department "
            + "and date = :date and user = :user";

        List<MFTScore> scores = entityManager.createQuery( query,
            MFTScore.class )
            .setParameter( "department", department )
            .setParameter( "date", date )
            .setParameter( "user", user )
            .getResultList();
        return scores.size() == 0 ? null : scores.get( 0 );
    }

    @Override
    public List<MFTScore> getScores( Department department, Date date )
    {
        String query = "from MFTScore where date = :date order by value desc";

        return entityManager.createQuery( query, MFTScore.class )
            .setParameter( "date", date )
            .getResultList();
    }

    @Override
    public List<MFTScore> getScores( Department department, Integer year )
    {
        String query = "from MFTScore where year(date) = :year";

        return entityManager.createQuery( query, MFTScore.class )
            .setParameter( "year", year )
            .getResultList();
    }

    @Override
    public List<MFTScore> getMedianScores( Department department,
        Integer beginYear, Integer endYear )
    {
        return entityManager.createNamedQuery( "mft.median.scores",
            MFTScore.class )
            .setParameter( "departmentId", department.getId() )
            .setParameter( "beginYear", beginYear )
            .setParameter( "endYear", endYear )
            .getResultList();
    }

    @Override
    @Transactional
    public MFTScore saveScore( MFTScore score )
    {
        return entityManager.merge( score );
    }

}
