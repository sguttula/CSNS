package csns.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csns.model.academics.Assignment;
import csns.model.academics.Enrollment;
import csns.model.academics.Section;
import csns.model.academics.Submission;
import csns.model.core.User;

public class GradeSheet {

    private final Section section;

    Map<Enrollment, String[]> studentGrades;

    public GradeSheet( Section section )
    {
        this.section = section;

        studentGrades = new HashMap<Enrollment, String[]>();
        Map<User, String[]> userIndexedGrades = new HashMap<User, String[]>();

        for( Enrollment enrollment : section.getEnrollments() )
        {
            String grades[] = new String[section.getAssignments().size()];
            studentGrades.put( enrollment, grades );
            userIndexedGrades.put( enrollment.getStudent(),
                studentGrades.get( enrollment ) );
        }

        List<Assignment> assignments = section.getAssignments();
        for( int i = 0; i < assignments.size(); ++i )
        {
            for( Submission submission : assignments.get( i ).getSubmissions() )
            {
                String grades[] = userIndexedGrades.get( submission.getStudent() );
                if( grades != null ) grades[i] = submission.getGrade();
            }
        }
    }

    public Section getSection()
    {
        return section;
    }

    public Map<Enrollment, String[]> getStudentGrades()
    {
        return studentGrades;
    }

    public void setStudentGrades( Map<Enrollment, String[]> studentGrades )
    {
        this.studentGrades = studentGrades;
    }

}
