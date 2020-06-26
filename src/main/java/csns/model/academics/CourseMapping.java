package csns.model.academics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "course_mappings")
public class CourseMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToMany
    @JoinTable(name = "course_mapping_group1",
        joinColumns = @JoinColumn(name = "mapping_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id"),
        uniqueConstraints = { @UniqueConstraint(columnNames = { "mapping_id",
            "course_id" }) })
    @OrderBy("code asc")
    private List<Course> group1;

    @ManyToMany
    @JoinTable(name = "course_mapping_group2",
        joinColumns = @JoinColumn(name = "mapping_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id"),
        uniqueConstraints = { @UniqueConstraint(columnNames = { "mapping_id",
            "course_id" }) })
    @OrderBy("code asc")
    private List<Course> group2;

    private boolean deleted;

    public CourseMapping()
    {
        group1 = new ArrayList<Course>();
        group2 = new ArrayList<Course>();

        deleted = false;
    }

    public CourseMapping( Department department )
    {
        this();
        this.department = department;
    }

    public boolean contains( Course course )
    {
        for( Course c : group1 )
            if( c.getId().equals( course.getId() ) ) return true;

        for( Course c : group2 )
            if( c.getId().equals( course.getId() ) ) return true;

        return false;
    }

    public void removeCourse( Long courseId )
    {
        for( Course course : group1 )
            if( course.getId().equals( courseId ) )
            {
                group1.remove( course );
                break;
            }

        for( Course course : group2 )
            if( course.getId().equals( courseId ) )
            {
                group2.remove( course );
                break;
            }
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

    public List<Course> getGroup1()
    {
        return group1;
    }

    public void setGroup1( List<Course> group1 )
    {
        this.group1 = group1;
    }

    public List<Course> getGroup2()
    {
        return group2;
    }

    public void setGroup2( List<Course> group2 )
    {
        this.group2 = group2;
    }

    public boolean isDeleted()
    {
        return deleted;
    }

    public void setDeleted( boolean deleted )
    {
        this.deleted = deleted;
    }

}
