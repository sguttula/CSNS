package csns.model.academics.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.academics.Assignment;
import csns.model.academics.OnlineSubmission;
import csns.model.academics.Section;
import csns.model.academics.Submission;
import csns.model.academics.dao.SubmissionDao;
import csns.model.core.User;

@Repository
public class SubmissionDaoImpl implements SubmissionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @PostAuthorize("returnObject.student.id == principal.id or returnObject.assignment.section.isInstructor(principal) or principal.faculty")
    public Submission getSubmission( Long id )
    {
        return entityManager.find( Submission.class, id );
    }

    @Override
    public Submission getSubmission( User student, Assignment assignment )
    {
        String query = "from Submission submission "
            + "where student = :student and assignment = :assignment";

        List<Submission> submissions = entityManager.createQuery( query,
            Submission.class )
            .setParameter( "student", student )
            .setParameter( "assignment", assignment )
            .getResultList();
        return submissions.size() == 0 ? null : submissions.get( 0 );
    }

    @Override
    public List<Submission> getSubmissions( User student, Section section )
    {
        String query = "from Submission submission "
            + "where submission.student = :student "
            + "and submission.assignment.section = :section "
            + "and submission.assignment.deleted = false";

        return entityManager.createQuery( query, Submission.class )
            .setParameter( "student", student )
            .setParameter( "section", section )
            .getResultList();
    }

    @Override
    public OnlineSubmission findSubmission( Long answerSheetId )
    {
        List<OnlineSubmission> results = entityManager.createQuery(
            "from OnlineSubmission where answerSheet.id = :answerSheetId",
            OnlineSubmission.class )
            .setParameter( "answerSheetId", answerSheetId )
            .getResultList();
        return results.size() == 0 ? null : results.get( 0 );
    }

    @Override
    @Transactional
    @PreAuthorize("#submission.student.id == principal.id or #submission.assignment.section.isInstructor(principal)")
    public Submission saveSubmission( Submission submission )
    {
        return entityManager.merge( submission );
    }

}
