package csns.model.assessment.dao.jpa;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.academics.Department;
import csns.model.assessment.Rubric;
import csns.model.assessment.dao.RubricDao;
import csns.model.core.User;

@Repository
public class RubricDaoImpl implements RubricDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @PostAuthorize("returnObject.published or authenticated and (returnObject.creator.id == principal.id or principal.admin)")
    public Rubric getRubric( Long id )
    {
        return entityManager.find( Rubric.class, id );
    }

    @Override
    public List<Rubric> getDepartmentRubrics( Department department )
    {
        String query = "from Rubric where department = :department "
            + "and deleted = false order by name asc";

        return entityManager.createQuery( query, Rubric.class )
            .setParameter( "department", department )
            .getResultList();
    }

    @Override
    public List<Rubric> getPublishedDepartmentRubrics( Department department )
    {
        String query = "from Rubric where department = :department "
            + "and publishDate is not null and publishDate < :now "
            + "and obsolete = false and deleted = false order by name asc";

        return entityManager.createQuery( query, Rubric.class )
            .setParameter( "department", department )
            .setParameter( "now", Calendar.getInstance() )
            .getResultList();
    }

    @Override
    public List<Rubric> getPersonalRubrics( User creator )
    {
        String query = "from Rubric where department is null "
            + "and creator = :creator and deleted = false order by name asc";

        return entityManager.createQuery( query, Rubric.class )
            .setParameter( "creator", creator )
            .getResultList();
    }

    @Override
    public List<Rubric> getPublishedPersonalRubrics( User creator )
    {
        String query = "from Rubric where department is null "
            + "and creator = :creator and publishDate is not null "
            + "and publishDate < :now and obsolete = false "
            + "and deleted = false order by name asc";

        return entityManager.createQuery( query, Rubric.class )
            .setParameter( "creator", creator )
            .setParameter( "now", Calendar.getInstance() )
            .getResultList();
    }

    @Override
    public List<Rubric> searchRubrics( String text, int maxResults )
    {
        TypedQuery<Rubric> query = entityManager
            .createNamedQuery( "rubric.search", Rubric.class );
        if( maxResults > 0 ) query.setMaxResults( maxResults );
        return query.setParameter( "text", text ).getResultList();
    }

    @Override
    @Transactional
    @PreAuthorize("authenticated and (#rubric.creator.id == principal.id or principal.admin)")
    public Rubric saveRubric( Rubric rubric )
    {
        return entityManager.merge( rubric );
    }

}
