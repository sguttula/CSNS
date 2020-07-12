package csns.model.advisement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import csns.model.academics.Course;
import csns.model.academics.Enrollment;

@Entity
@Table(name = "personal_program_entries")
public class PersonalProgramEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;

    @Column(name = "prereq_met", nullable = false)
    private boolean prereqMet = false;

    @Column(name = "requirement_met", nullable = false)
    private boolean requirementMet = false;

    public PersonalProgramEntry()
    {
    }

    public PersonalProgramEntry( Course course )
    {
        this.course = course;
    }

    public PersonalProgramEntry( Enrollment enrollment )
    {
        this.enrollment = enrollment;
        this.course = enrollment.getSection().getCourse();
    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public Course getCourse()
    {
        return course;
    }

    public void setCourse( Course course )
    {
        this.course = course;
    }

    public Enrollment getEnrollment()
    {
        return enrollment;
    }

    public void setEnrollment( Enrollment enrollment )
    {
        this.enrollment = enrollment;
    }

    public boolean isPrereqMet()
    {
        return prereqMet;
    }

    public void setPrereqMet( boolean prereqMet )
    {
        this.prereqMet = prereqMet;
    }

    public boolean isRequirementMet()
    {
        return requirementMet;
    }

    public void setRequirementMet( boolean requirementMet )
    {
        this.requirementMet = requirementMet;
    }

}
