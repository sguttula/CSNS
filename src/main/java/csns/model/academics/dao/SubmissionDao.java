package csns.model.academics.dao;

import java.util.List;

import csns.model.academics.Assignment;
import csns.model.academics.OnlineSubmission;
import csns.model.academics.Section;
import csns.model.academics.Submission;
import csns.model.core.User;

public interface SubmissionDao {

    Submission getSubmission( Long id );

    Submission getSubmission( User student, Assignment assignment );

    List<Submission> getSubmissions( User student, Section section );

    OnlineSubmission findSubmission( Long answerSheetId );

    Submission saveSubmission( Submission submission );

}
