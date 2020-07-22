package csns.model.assessment.dao.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.academics.Department;
import csns.model.assessment.MFTIndicator;
import csns.model.assessment.dao.MFTIndicatorDao;

@Repository
public class MFTIndicatorDaoImpl implements MFTIndicatorDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Integer> getYears( Department department )
    {
        String query = "select distinct year(date) from MFTIndicator "
            + "where department = :department and deleted = false "
            + "order by year(date) asc";

        return entityManager.createQuery( query, Integer.class )
            .setParameter( "department", department )
            .getResultList();
    }

    @Override
    public MFTIndicator getIndicator( Long id )
    {
        return entityManager.find( MFTIndicator.class, id );
    }

    @Override
    public MFTIndicator getIndicator( Department department, Date date )
    {
        String query = "from MFTIndicator where department = :department "
            + "and date = :date";

        List<MFTIndicator> indicators = entityManager.createQuery( query,
            MFTIndicator.class )
            .setParameter( "department", department )
            .setParameter( "date", date )
            .getResultList();
        return indicators.size() == 0 ? null : indicators.get( 0 );
    }

    @Override
    public List<MFTIndicator> getIndicators( Department department )
    {
        String query = "from MFTIndicator where department = :department "
            + "and deleted = false order by date desc";

        return entityManager.createQuery( query, MFTIndicator.class )
            .setParameter( "department", department )
            .getResultList();
    }

    @Override
    public List<MFTIndicator> getIndicators( Department department,
        Integer beginYear, Integer endYear )
    {
        String query = "from MFTIndicator where department = :department "
            + "and year(date) >= :beginYear and year(date) <= :endYear "
            + "and deleted = false order by date asc";

        return entityManager.createQuery( query, MFTIndicator.class )
            .setParameter( "department", department )
            .setParameter( "beginYear", beginYear )
            .setParameter( "endYear", endYear )
            .getResultList();
    }

    @Override
    @Transactional
    public MFTIndicator saveIndicator( MFTIndicator indicator )
    {
        return entityManager.merge( indicator );
    }

}
