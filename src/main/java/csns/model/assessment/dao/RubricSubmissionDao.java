package csns.model.assessment.dao;

import java.util.List;

import csns.model.academics.Section;
import csns.model.assessment.RubricAssignment;
import csns.model.assessment.RubricSubmission;
import csns.model.core.User;

public interface RubricSubmissionDao {

    RubricSubmission getRubricSubmission( Long id );

    RubricSubmission getRubricSubmission( User student,
        RubricAssignment assignment );

    List<RubricSubmission> getRubricSubmissions( User student,
        Section section );
 
    RubricSubmission saveRubricSubmission( RubricSubmission submission );

}
