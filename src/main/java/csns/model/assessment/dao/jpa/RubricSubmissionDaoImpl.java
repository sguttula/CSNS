package csns.model.assessment.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.academics.Section;
import csns.model.assessment.RubricAssignment;
import csns.model.assessment.RubricSubmission;
import csns.model.assessment.dao.RubricSubmissionDao;
import csns.model.core.User;

@Repository
public class RubricSubmissionDaoImpl implements RubricSubmissionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public RubricSubmission getRubricSubmission( Long id )
    {
        return entityManager.find( RubricSubmission.class, id );
    }

    @Override
    public RubricSubmission getRubricSubmission( User student,
        RubricAssignment assignment )
    {
        String query = "from RubricSubmission where student = :student "
            + "and assignment = :assignment";

        List<RubricSubmission> results = entityManager
            .createQuery( query, RubricSubmission.class )
            .setParameter( "student", student )
            .setParameter( "assignment", assignment )
            .getResultList();
        return results.size() == 0 ? null : results.get( 0 );
    }

    @Override
    public List<RubricSubmission> getRubricSubmissions( User student,
        Section section )
    {
        String query = "from RubricSubmission submission "
            + "where submission.student = :student "
            + "and submission.assignment.section = :section "
            + "and submission.assignment.deleted = false";

        return entityManager.createQuery( query, RubricSubmission.class )
            .setParameter( "student", student )
            .setParameter( "section", section )
            .getResultList();
    }

    @Override
    @Transactional
    public RubricSubmission saveRubricSubmission( RubricSubmission submission )
    {
        return entityManager.merge( submission );
    }

}
