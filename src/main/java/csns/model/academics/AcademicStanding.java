package csns.model.academics;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import csns.model.core.User;

@Entity
@Table(name = "academic_standings",
    uniqueConstraints = @UniqueConstraint(columnNames = { "student_id",
        "department_id", "standing_id" }))
public class AcademicStanding implements Serializable,
    Comparable<AcademicStanding> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "standing_id")
    private Standing standing;

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "code",
        column = @Column(name = "term")) })
    private Term term;

    public AcademicStanding()
    {
    }

    public AcademicStanding( User student, Department department,
        Standing standing, Term term )
    {
        this.student = student;
        this.department = department;
        this.standing = standing;
        this.term = term;
    }

    @Override
    public int compareTo( AcademicStanding academicStanding )
    {
        if( academicStanding == null )
            throw new IllegalArgumentException( "Cannot compare to NULL." );

        int cmp = department.getName().compareTo(
            academicStanding.department.getName() );
        if( cmp == 0 ) cmp = term.compareTo( academicStanding.term );
        if( cmp == 0 ) cmp = standing.compareTo( academicStanding.standing );

        return cmp;
    }

    @Override
    public String toString()
    {
        return "[" + student.getCin() + ", " + department.getAbbreviation()
            + ", " + standing.getSymbol() + ", " + term.getShortString()
            + "]";
    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public User getStudent()
    {
        return student;
    }

    public void setStudent( User student )
    {
        this.student = student;
    }

    public Department getDepartment()
    {
        return department;
    }

    public void setDepartment( Department department )
    {
        this.department = department;
    }

    public Standing getStanding()
    {
        return standing;
    }

    public void setStanding( Standing standing )
    {
        this.standing = standing;
    }

    public Term getTerm()
    {
        return term;
    }

    public void setTerm( Term term )
    {
        this.term = term;
    }

}
