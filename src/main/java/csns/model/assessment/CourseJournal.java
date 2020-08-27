package csns.model.assessment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import csns.model.academics.Assignment;
import csns.model.academics.Enrollment;
import csns.model.academics.Section;
import csns.model.core.Resource;

@Entity
@Table(name = "course_journals")
public class CourseJournal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "journal")
    @JoinColumn(name = "section_id")
    private Section section;

    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(name = "course_journal_handouts",
        joinColumns = @JoinColumn(name = "course_journal_id"),
        inverseJoinColumns = @JoinColumn(name = "resource_id"))
    @OrderColumn(name = "handout_order")
    private List<Resource> handouts;

    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(name = "course_journal_assignments",
        joinColumns = @JoinColumn(name = "course_journal_id"),
        inverseJoinColumns = @JoinColumn(name = "assignment_id"))
    @OrderColumn(name = "assignment_order")
    private List<Assignment> assignments;

    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(name = "course_journal_rubric_assignments",
        joinColumns = @JoinColumn(name = "course_journal_id"),
        inverseJoinColumns = @JoinColumn(name = "assignment_id"))
    @OrderColumn(name = "assignment_order")
    private List<RubricAssignment> rubricAssignments;

    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(name = "course_journal_student_samples",
        joinColumns = @JoinColumn(name = "course_journal_id"),
        inverseJoinColumns = @JoinColumn(name = "enrollment_id"),
        uniqueConstraints = @UniqueConstraint(
            columnNames = { "course_journal_id", "enrollment_id" }))
    @OrderBy("id asc")
    private List<Enrollment> studentSamples;

    @Column(name = "submit_date")
    private Date submitDate;

    @Column(name = "approve_date")
    private Date approveDate;

    public CourseJournal()
    {
        handouts = new ArrayList<Resource>();
        assignments = new ArrayList<Assignment>();
        rubricAssignments = new ArrayList<RubricAssignment>();
        studentSamples = new ArrayList<Enrollment>();
    }

    public CourseJournal( Section section )
    {
        this();
        this.section = section;
    }

    public boolean isSubmitted()
    {
        return submitDate != null;
    }

    public boolean isApproved()
    {
        return approveDate != null;
    }

    public boolean removeHandout( Long resourceId )
    {
        for( Resource handout : handouts )
            if( handout.getId().equals( resourceId ) )
                return handouts.remove( handout );

        return false;
    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public Section getSection()
    {
        return section;
    }

    public void setSection( Section section )
    {
        this.section = section;
    }

    public List<Resource> getHandouts()
    {
        return handouts;
    }

    public void setHandouts( List<Resource> handouts )
    {
        this.handouts = handouts;
    }

    public List<Assignment> getAssignments()
    {
        return assignments;
    }

    public void setAssignments( List<Assignment> assignments )
    {
        this.assignments = assignments;
    }

    public List<RubricAssignment> getRubricAssignments()
    {
        return rubricAssignments;
    }

    public void setRubricAssignments( List<RubricAssignment> rubricAssignments )
    {
        this.rubricAssignments = rubricAssignments;
    }

    public List<Enrollment> getStudentSamples()
    {
        return studentSamples;
    }

    public void setStudentSamples( List<Enrollment> studentSamples )
    {
        this.studentSamples = studentSamples;
    }

    public Date getSubmitDate()
    {
        return submitDate;
    }

    public void setSubmitDate( Date submitDate )
    {
        this.submitDate = submitDate;
    }

    public Date getApproveDate()
    {
        return approveDate;
    }

    public void setApproveDate( Date approveDate )
    {
        this.approveDate = approveDate;
    }

}
