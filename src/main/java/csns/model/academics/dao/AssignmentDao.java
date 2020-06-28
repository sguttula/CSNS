package csns.model.academics.dao;

import java.util.List;

import csns.model.academics.Assignment;
import csns.model.academics.OnlineAssignment;
import csns.model.academics.Section;
import csns.model.core.User;

public interface AssignmentDao {

    Assignment getAssignment( Long id );

    List<Assignment> searchAssignments( String text, String type,
        User instructor, int maxResults );

    List<OnlineAssignment> getOnlineAssignments( Section section );

    List<OnlineAssignment> getOnlineAssignments( User instructor );

    Assignment saveAssignment( Assignment assignment );

}
