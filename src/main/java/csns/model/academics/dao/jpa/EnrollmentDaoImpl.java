package csns.model.academics.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.academics.Course;
import csns.model.academics.Enrollment;
import csns.model.academics.Section;
import csns.model.academics.Term;
import csns.model.academics.dao.EnrollmentDao;
import csns.model.core.User;

@Repository
public class EnrollmentDaoImpl implements EnrollmentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @PostAuthorize("returnObject.section.isInstructor(principal) or returnObject.student.id == principal.id or principal.faculty")
    public Enrollment getEnrollment( Long id )
    {
        return entityManager.find( Enrollment.class, id );
    }

    @Override
    public Enrollment getEnrollment( Section section, User student )
    {
        String query = "from Enrollment where section = :section "
            + "and student = :student";

        List<Enrollment> enrollments = entityManager
            .createQuery( query, Enrollment.class )
            .setParameter( "section", section )
            .setParameter( "student", student )
            .getResultList();
        return enrollments.size() == 0 ? null : enrollments.get( 0 );
    }

    @Override
    public List<Enrollment> getEnrollments( User student )
    {
        String query = "from Enrollment where student = :student "
            + "order by section.term desc";

        return entityManager.createQuery( query, Enrollment.class )
            .setParameter( "student", student )
            .getResultList();
    }

    @Override
    public List<Enrollment> getEnrollments( Course course, Term term,
        User student )
    {
        String query = "from Enrollment where section.course = :course "
            + "and section.term = :term and student = :student "
            + "order by id asc";

        return entityManager.createQuery( query, Enrollment.class )
            .setParameter( "course", course )
            .setParameter( "term", term )
            .setParameter( "student", student )
            .getResultList();
    }

    @Override
    @Transactional
    @PreAuthorize("#enrollment.section.isInstructor(principal) or principal.admin")
    public Enrollment saveEnrollment( Enrollment enrollment )
    {
        return entityManager.merge( enrollment );
    }

    @Override
    @Transactional
    @PreAuthorize("#enrollment.section.isInstructor(principal) or principal.admin")
    public void deleteEnrollment( Enrollment enrollment )
    {
        entityManager.remove( enrollment );
    }

}
