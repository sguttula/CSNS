package csns.model.academics;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import csns.model.core.User;

@Entity
@Table(name = "enrollments",
    uniqueConstraints = @UniqueConstraint(
        columnNames = { "section_id", "student_id" }))
public class Enrollment implements Serializable, Comparable<Enrollment> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;

    private String comments;

    @Column(name = "grade_mailed", nullable = false)
    private boolean gradeMailed = false;

    public Enrollment()
    {
    }

    public Enrollment( Section section, User student )
    {
        this( section, student, null );
    }

    public Enrollment( Section section, User student, Grade grade )
    {
        this.section = section;
        this.student = student;
        this.grade = grade;
    }

    @Override
    public int compareTo( Enrollment enrollment )
    {
        if( enrollment == null )
            throw new IllegalArgumentException( "Cannot compare to NULL." );

        int cmp = section.compareTo( enrollment.section );
        if( cmp != 0 ) return cmp;

        return student.compareTo( enrollment.student );
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

    public User getStudent()
    {
        return student;
    }

    public void setStudent( User student )
    {
        this.student = student;
    }

    public Grade getGrade()
    {
        return grade;
    }

    public void setGrade( Grade grade )
    {
        this.grade = grade;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments( String comments )
    {
        this.comments = comments;
    }

    public boolean isGradeMailed()
    {
        return gradeMailed;
    }

    public void setGradeMailed( boolean gradeMailed )
    {
        this.gradeMailed = gradeMailed;
    }

}
