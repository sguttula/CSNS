package csns.model.assessment.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
 
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.academics.Department;
import csns.model.assessment.Program;
import csns.model.assessment.dao.ProgramDao;

@Repository("assessmentProgramDaoImpl")
public class ProgramDaoImpl implements ProgramDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Program getProgram( Long id )
    {
        return entityManager.find( Program.class, id );
    }

    @Override
    public List<Program> getPrograms( Department department )
    {
        String query = "from AssessmentProgram where department = :department "
            + "and deleted = false order by id asc";

        return entityManager.createQuery( query, Program.class )
            .setParameter( "department", department )
            .getResultList();
    }

    @Override
    @Transactional
    public Program saveProgram( Program program )
    {
        return entityManager.merge( program );
    }

}
