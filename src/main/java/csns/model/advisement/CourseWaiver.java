package csns.model.advisement;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import csns.model.academics.Course;

@Entity
@Table(name = "course_waivers")
public class CourseWaiver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST },
        fetch = FetchType.LAZY)
    @JoinColumn(name = "advisement_record_id")
    private AdvisementRecord advisementRecord;

    public CourseWaiver()
    {
    }

    public CourseWaiver( Course course, AdvisementRecord advisementRecord )
    {
        this.course = course;
        this.advisementRecord = advisementRecord;
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

    public AdvisementRecord getAdvisementRecord()
    {
        return advisementRecord;
    }

    public void setAdvisementRecord( AdvisementRecord advisementRecord )
    {
        this.advisementRecord = advisementRecord;
    }

}
