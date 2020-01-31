package csns.model.academics.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.academics.Department;
import csns.model.academics.Project;
import csns.model.academics.dao.ProjectDao;

@Repository
public class ProjectDaoImpl implements ProjectDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Project getProject( Long id )
    {
        return entityManager.find( Project.class, id );
    }

    @Override
    public List<Project> getProjects( Department department, int year )
    {
        String query = "from Project where department = :department "
            + "and year = :year and deleted = false order by title asc";

        return entityManager.createQuery( query, Project.class )
            .setParameter( "department", department )
            .setParameter( "year", year )
            .getResultList();
    }

    @Override
    public List<Integer> getProjectYears( Department department )
    {
        String query = "select distinct year from Project "
            + "where department = :department order by year desc";

        return entityManager.createQuery( query, Integer.class )
            .setParameter( "department", department )
            .getResultList();
    }

    @Override
    public List<Project> searchProjects( String text, int maxResults )
    {
        TypedQuery<Project> query = entityManager.createNamedQuery(
            "project.search", Project.class );
        if( maxResults > 0 ) query.setMaxResults( maxResults );
        return query.setParameter( "text", text ).getResultList();
    }

    @Override
    @Transactional
    @PreAuthorize("authenticated and (principal.faculty or #project.id != null and #project.isMember(principal))")
    public Project saveProject( Project project )
    {
        return entityManager.merge( project );
    }

}
