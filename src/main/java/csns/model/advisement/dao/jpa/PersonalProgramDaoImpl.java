package csns.model.advisement.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.academics.Program;
import csns.model.advisement.PersonalProgram;
import csns.model.advisement.PersonalProgramBlock;
import csns.model.advisement.PersonalProgramEntry;
import csns.model.advisement.dao.PersonalProgramDao;
import csns.model.core.User;

@Repository
public class PersonalProgramDaoImpl implements PersonalProgramDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PersonalProgram getPersonalProgram( Long id )
    {
        return entityManager.find( PersonalProgram.class, id );
    }

    @Override
    public PersonalProgram getPersonalProgram( User student, Program program )
    {
        String query = "from PersonalProgram where student = :student "
            + "and program = :program order by date desc";

        List<PersonalProgram> personalPrograms = entityManager
            .createQuery( query, PersonalProgram.class )
            .setParameter( "student", student )
            .setParameter( "program", program )
            .getResultList();
        return personalPrograms.size() == 0 ? null : personalPrograms.get( 0 );
    }

    @Override
    public PersonalProgram getPersonalProgram( PersonalProgramBlock block )
    {
        String query = "select program from PersonalProgram program "
            + "join program.blocks block where block = :block";

        List<PersonalProgram> personalPrograms = entityManager
            .createQuery( query, PersonalProgram.class )
            .setParameter( "block", block )
            .getResultList();
        return personalPrograms.size() == 0 ? null : personalPrograms.get( 0 );
    }

    @Override
    public PersonalProgram getPersonalProgram( PersonalProgramEntry entry )
    {
        String query = "select program from PersonalProgram program "
            + "join program.blocks block join block.entries entry "
            + "where entry = :entry";

        List<PersonalProgram> personalPrograms = entityManager
            .createQuery( query, PersonalProgram.class )
            .setParameter( "entry", entry )
            .getResultList();
        return personalPrograms.size() == 0 ? null : personalPrograms.get( 0 );
    }

    @Override
    public List<PersonalProgram> getPersonalPrograms( User student )
    {
        String query = "from PersonalProgram where student = :student "
            + "order by date desc";

        return entityManager.createQuery( query, PersonalProgram.class )
            .setParameter( "student", student )
            .getResultList();
    }

    @Override
    @Transactional
    public PersonalProgram savePersonalProgram( PersonalProgram program )
    {
        return entityManager.merge( program );
    }

}
