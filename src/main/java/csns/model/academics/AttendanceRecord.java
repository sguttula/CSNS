package csns.model.academics;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import csns.model.core.User;

@Entity
@Table(name = "attendance_records",
    uniqueConstraints = @UniqueConstraint(columnNames = { "event_id", "user_id" }))
public class AttendanceRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private AttendanceEvent event;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Boolean attended;

    public AttendanceRecord()
    {
    }

    public AttendanceRecord( AttendanceEvent event, User user )
    {
        this.user = user;
        this.event = event;
    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public AttendanceEvent getEvent()
    {
        return event;
    }

    public void setEvent( AttendanceEvent event )
    {
        this.event = event;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser( User user )
    {
        this.user = user;
    }

    public Boolean getAttended()
    {
        return attended;
    }

    public void setAttended( Boolean attended )
    {
        this.attended = attended;
    }

}
