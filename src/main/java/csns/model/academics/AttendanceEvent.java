package csns.model.academics;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import csns.model.core.User;

@Entity
@Table(name = "attendance_events")
public class AttendanceEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "event")
    @MapKey(name = "user")
    private Map<User, AttendanceRecord> records;

    public Boolean isAttended( User user )
    {
        AttendanceRecord record = records.get( user );
        return record != null ? record.getAttended() : null;
    }

    public AttendanceEvent()
    {
        records = new HashMap<User, AttendanceRecord>();
    }

    public AttendanceEvent( String name )
    {
        this.name = name;
    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public Map<User, AttendanceRecord> getRecords()
    {
        return records;
    }

    public void setRecords( Map<User, AttendanceRecord> records )
    {
        this.records = records;
    }

}
