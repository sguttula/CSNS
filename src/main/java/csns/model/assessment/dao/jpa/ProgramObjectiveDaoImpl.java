package csns.model.assessment.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.assessment.ProgramObjective;
import csns.model.assessment.dao.ProgramObjectiveDao;

@Repository
public class ProgramObjectiveDaoImpl implements ProgramObjectiveDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ProgramObjective getProgramObjective( Long id )
    {
        return entityManager.find( ProgramObjective.class, id );
    }

    @Override
    @Transactional
    public ProgramObjective saveProgramObjective( ProgramObjective objective )
    {
        return entityManager.merge( objective );
    }

}
