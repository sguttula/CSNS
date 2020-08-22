package csns.model.assessment.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.assessment.ProgramOutcome;
import csns.model.assessment.dao.ProgramOutcomeDao;

@Repository
public class ProgramOutcomeDaoImpl implements ProgramOutcomeDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ProgramOutcome getProgramOutcome( Long id )
    {
        return entityManager.find( ProgramOutcome.class, id );
    }

    @Override
    @Transactional
    public ProgramOutcome saveProgramOutcome( ProgramOutcome outcome )
    {
        return entityManager.merge( outcome );
    }

}
