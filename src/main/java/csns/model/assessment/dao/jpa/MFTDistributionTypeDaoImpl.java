package csns.model.assessment.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import csns.model.academics.Department;
import csns.model.assessment.MFTDistributionType;
import csns.model.assessment.dao.MFTDistributionTypeDao;

@Repository
public class MFTDistributionTypeDaoImpl implements MFTDistributionTypeDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public MFTDistributionType getDistributionType( Long id )
    {
        return entityManager.find( MFTDistributionType.class, id );
    }

    @Override
    public MFTDistributionType getDistributionType( Department department,
        String alias )
    {
        String query = "from MFTDistributionType where department = :department "
            + "and alias = :alias";

        List<MFTDistributionType> distributionTypes = entityManager.createQuery(
            query, MFTDistributionType.class )
            .setParameter( "department", department )
            .setParameter( "alias", alias )
            .getResultList();
        return distributionTypes.size() == 0 ? null : distributionTypes.get( 0 );
    }

    @Override
    public List<MFTDistributionType> getDistributionTypes( Department department )
    {
        String query = "from MFTDistributionType where department = :department "
            + "order by id asc";

        return entityManager.createQuery( query, MFTDistributionType.class )
            .setParameter( "department", department )
            .getResultList();
    }

}
