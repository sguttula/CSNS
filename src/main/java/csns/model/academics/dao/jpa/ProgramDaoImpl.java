package csns.model.academics.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.academics.Department;
import csns.model.academics.Program;
import csns.model.academics.dao.ProgramDao;

@Repository
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
        String query = "from Program where department = :department "
            + "and obsolete = false order by name asc";

        return entityManager.createQuery( query, Program.class )
            .setParameter( "department", department )
            .getResultList();
    }

    @Override
    public List<Program> getPublishedPrograms( Department department )
    {
        String query = "from Program where department = :department "
            + "and publishDate < current_timestamp and obsolete = false "
            + "order by name asc";

        return entityManager.createQuery( query, Program.class )
            .setParameter( "department", department )
            .getResultList();
    }

    @Override
    public List<Program> searchPrograms( String text, int maxResults )
    {
        TypedQuery<Program> query = entityManager
            .createNamedQuery( "program.search", Program.class );
        if( maxResults > 0 ) query.setMaxResults( maxResults );
        return query.setParameter( "text", text ).getResultList();
    }

    @Override
    @Transactional
    public Program saveProgram( Program program )
    {
        return entityManager.merge( program );
    }

}
