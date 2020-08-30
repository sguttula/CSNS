package csns.model.assessment;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import csns.model.academics.Department;

@Entity
@Table(name = "mft_indicators",
    uniqueConstraints = @UniqueConstraint(columnNames = { "department_id",
        "date" }))
public class MFTIndicator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Integer ai1, ai2, ai3;

    @Column(name = "num_of_students", nullable = false)
    private Integer numOfStudents;

    private boolean deleted;

    @Transient
    private Integer ai1Percentile, ai2Percentile, ai3Percentile;

    public MFTIndicator()
    {
        deleted = false;
    }

    public MFTIndicator merge( MFTIndicator other )
    {
        ai1 = (ai1 * numOfStudents + other.ai1 * other.numOfStudents)
            / (numOfStudents + other.numOfStudents);
        ai2 = (ai2 * numOfStudents + other.ai2 * other.numOfStudents)
            / (numOfStudents + other.numOfStudents);
        ai3 = (ai3 * numOfStudents + other.ai3 * other.numOfStudents)
            / (numOfStudents + other.numOfStudents);

        numOfStudents += other.numOfStudents;

        return this;
    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public Department getDepartment()
    {
        return department;
    }

    public void setDepartment( Department department )
    {
        this.department = department;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate( Date date )
    {
        this.date = date;
    }

    public Integer getAi1()
    {
        return ai1;
    }

    public void setAi1( Integer ai1 )
    {
        this.ai1 = ai1;
    }

    public Integer getAi2()
    {
        return ai2;
    }

    public void setAi2( Integer ai2 )
    {
        this.ai2 = ai2;
    }

    public Integer getAi3()
    {
        return ai3;
    }

    public void setAi3( Integer ai3 )
    {
        this.ai3 = ai3;
    }

    public Integer getNumOfStudents()
    {
        return numOfStudents;
    }

    public void setNumOfStudents( Integer numOfStudents )
    {
        this.numOfStudents = numOfStudents;
    }

    public boolean isDeleted()
    {
        return deleted;
    }

    public void setDeleted( boolean deleted )
    {
        this.deleted = deleted;
    }

    public Integer getAi1Percentile()
    {
        return ai1Percentile;
    }

    public void setAi1Percentile( Integer ai1Percentile )
    {
        this.ai1Percentile = ai1Percentile;
    }

    public Integer getAi2Percentile()
    {
        return ai2Percentile;
    }

    public void setAi2Percentile( Integer ai2Percentile )
    {
        this.ai2Percentile = ai2Percentile;
    }

    public Integer getAi3Percentile()
    {
        return ai3Percentile;
    }

    public void setAi3Percentile( Integer ai3Percentile )
    {
        this.ai3Percentile = ai3Percentile;
    }

}
