package csns.model.academics.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.academics.Assignment;
import csns.model.academics.OnlineAssignment;
import csns.model.academics.Section;
import csns.model.academics.dao.AssignmentDao;
import csns.model.core.User;

@Repository
public class AssignmentDaoImpl implements AssignmentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @PostAuthorize("returnObject.section.isInstructor(principal) or returnObject.section.isEnrolled(principal) or principal.admin")
    public Assignment getAssignment( Long id )
    {
        return entityManager.find( Assignment.class, id );
    }

    @Override
    public List<Assignment> searchAssignments( String text, String type,
        User instructor, int maxResults )
    {
        TypedQuery<Assignment> query = entityManager.createNamedQuery(
            "assignment.search", Assignment.class );
        if( maxResults > 0 ) query.setMaxResults( maxResults );

        return query.setParameter( "text", text )
            .setParameter( "type", type )
            .setParameter( "instructorId", instructor.getId() )
            .getResultList();
    }

    @Override
    public List<OnlineAssignment> getOnlineAssignments( Section section )
    {
        String query = "from OnlineAssignment where section = :section "
            + "order by name asc";

        return entityManager.createQuery( query, OnlineAssignment.class )
            .setParameter( "section", section )
            .getResultList();
    }

    @Override
    public List<OnlineAssignment> getOnlineAssignments( User instructor )
    {
        String query = "select assignment from OnlineAssignment assignment "
            + "join assignment.section.instructors instructor "
            + "where instructor = :instructor order by assignment.dueDate desc";

        return entityManager.createQuery( query, OnlineAssignment.class )
            .setParameter( "instructor", instructor )
            .getResultList();
    }

    @Override
    @Transactional
    @PreAuthorize("#assignment.section.isInstructor(principal)")
    public Assignment saveAssignment( Assignment assignment )
    {
        return entityManager.merge( assignment );
    }

}
