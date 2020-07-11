package csns.model.advisement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import csns.model.academics.Course;
import csns.model.academics.Enrollment;
import csns.model.academics.Grade;
import csns.model.academics.Program;
import csns.model.academics.ProgramBlock;
import csns.model.core.User;

@Entity
@Table(name = "personal_programs",
    uniqueConstraints = @UniqueConstraint(
        columnNames = { "program_id", "student_id" }))
public class PersonalProgram implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "program_id")
    @OrderColumn(name = "block_index")
    private List<PersonalProgramBlock> blocks;

    @Column(nullable = false)
    private Date date;

    @Column(name = "approve_date")
    private Date approveDate;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    public PersonalProgram()
    {
        blocks = new ArrayList<PersonalProgramBlock>();
    }

    public PersonalProgram( Program program )
    {
        this();
        this.program = program;

        for( ProgramBlock programBlock : program.getBlocks() )
            blocks.add( new PersonalProgramBlock( programBlock ) );
    }

    public Double getGpa()
    {
        double units = 0;
        double total = 0;
        for( Enrollment enrollment : getEnrollments() )
        {
            Grade grade = enrollment.getGrade();
            if( grade != null && grade.getValue() != null )
            {
                Course course = enrollment.getSection().getCourse();
                units += course.getUnits() * course.getUnitFactor();
                total += grade.getValue() * course.getUnits()
                    * course.getUnitFactor();
            }
        }
        return units > 0 ? total / units : null;
    }

    public Set<Enrollment> getEnrollments()
    {
        Set<Enrollment> enrollments = new HashSet<Enrollment>();
        for( PersonalProgramBlock block : blocks )
            for( PersonalProgramEntry entry : block.getEntries() )
                if( entry.getEnrollment() != null )
                    enrollments.add( entry.getEnrollment() );
        return enrollments;
    }

    public List<Map<Course, PersonalProgramEntry>> getEntryMaps()
    {
        List<Map<Course, PersonalProgramEntry>> entryMaps = new ArrayList<Map<Course, PersonalProgramEntry>>();
        for( PersonalProgramBlock block : blocks )
            entryMaps.add( block.getEntryMap() );
        return entryMaps;
    }

    public boolean isApproved()
    {
        return approveDate != null && approvedBy != null;
    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public Program getProgram()
    {
        return program;
    }

    public void setProgram( Program program )
    {
        this.program = program;
    }

    public User getStudent()
    {
        return student;
    }

    public void setStudent( User student )
    {
        this.student = student;
    }

    public List<PersonalProgramBlock> getBlocks()
    {
        return blocks;
    }

    public void setBlocks( List<PersonalProgramBlock> blocks )
    {
        this.blocks = blocks;
    }

    public Date getApproveDate()
    {
        return approveDate;
    }

    public void setApproveDate( Date approveDate )
    {
        this.approveDate = approveDate;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate( Date date )
    {
        this.date = date;
    }

    public User getApprovedBy()
    {
        return approvedBy;
    }

    public void setApprovedBy( User approvedBy )
    {
        this.approvedBy = approvedBy;
    }

}
