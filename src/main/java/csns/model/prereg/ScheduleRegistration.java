package csns.model.prereg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import csns.model.academics.AcademicStanding;
import csns.model.core.User;

@Entity
@Table(name = "prereg_schedule_registrations",
    uniqueConstraints = @UniqueConstraint(
        columnNames = { "student_id", "schedule_id" }) )
public class ScheduleRegistration implements Serializable, Registration {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @Column(name = "reg_limit", nullable = false)
    private int regLimit;

    @OneToMany(mappedBy = "scheduleRegistration")
    private List<SectionRegistration> sectionRegistrations;

    private String comments;

    @Column(nullable = false)
    private Date date;

    public ScheduleRegistration()
    {
        regLimit = 5;
        date = new Date();
        sectionRegistrations = new ArrayList<SectionRegistration>();
    }

    public ScheduleRegistration( User student, Schedule schedule )
    {
        this();
        this.student = student;
        this.schedule = schedule;

        AcademicStanding academicStanding = student
            .getCurrentStanding( schedule.getDepartment() );
        if( academicStanding != null
            && academicStanding.getStanding().getSymbol().startsWith( "G" ) )
            regLimit = schedule.getDefaultGradRegLimit();
        else
            regLimit = schedule.getDefaultUndergradRegLimit();
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

    public Schedule getSchedule()
    {
        return schedule;
    }

    public void setSchedule( Schedule schedule )
    {
        this.schedule = schedule;
    }

    public int getRegLimit()
    {
        return regLimit;
    }

    public void setRegLimit( int regLimit )
    {
        this.regLimit = regLimit;
    }

    public List<SectionRegistration> getSectionRegistrations()
    {
        return sectionRegistrations;
    }

    public void setSectionRegistrations(
        List<SectionRegistration> sectionRegistrations )
    {
        this.sectionRegistrations = sectionRegistrations;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments( String comments )
    {
        this.comments = comments;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate( Date date )
    {
        this.date = date;
    }

}
