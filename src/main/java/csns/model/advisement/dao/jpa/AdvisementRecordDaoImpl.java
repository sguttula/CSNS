package csns.model.advisement.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.advisement.AdvisementRecord;
import csns.model.advisement.dao.AdvisementRecordDao;
import csns.model.core.User;

@Repository
public class AdvisementRecordDaoImpl implements AdvisementRecordDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public AdvisementRecord getAdvisementRecord( Long id )
    {
        return entityManager.find( AdvisementRecord.class, id );
    }

    @Override
    public List<AdvisementRecord> getAdvisementRecords( User student )
    {
        String query = "from AdvisementRecord where student = :student "
            + "order by date desc";

        return entityManager.createQuery( query, AdvisementRecord.class )
            .setParameter( "student", student )
            .getResultList();
    }

    @Override
    @Transactional
    @PreAuthorize("principal.id == #advisementRecord.advisor.id")
    public AdvisementRecord saveAdvisementRecord(
        AdvisementRecord advisementRecord )
    {
        return entityManager.merge( advisementRecord );
    }

}
