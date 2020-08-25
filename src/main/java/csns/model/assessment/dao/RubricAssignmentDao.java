package csns.model.assessment.dao;

import csns.model.assessment.RubricAssignment;

public interface RubricAssignmentDao {

    RubricAssignment getRubricAssignment( Long id );

    RubricAssignment saveRubricAssignment( RubricAssignment assignment );

}
