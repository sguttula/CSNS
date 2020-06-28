package csns.model.academics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import csns.model.academics.Course;

@Entity
@Table(name = "program_blocks")
public class ProgramBlock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    @Column(name = "units_required")
    private Integer unitsRequired;

    @Column(name = "require_all", nullable = false)
    private boolean requireAll;

    @ManyToMany
    @JoinTable(name = "program_block_courses",
        joinColumns = @JoinColumn(name = "block_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id"))
    @OrderBy("code asc")
    private List<Course> courses;

    public ProgramBlock()
    {
        requireAll = true;
        courses = new ArrayList<Course>();
    }

    public ProgramBlock clone()
    {
        ProgramBlock block = new ProgramBlock();

        block.name = name;
        block.description = description;
        block.unitsRequired = unitsRequired;
        block.requireAll = requireAll;
        block.courses.addAll( courses );

        return block;
    }

    public Course removeCourse( Long courseId )
    {
        for( int i = 0; i < courses.size(); ++i )
            if( courses.get( i ).getId().equals( courseId ) )
                return courses.remove( i );

        return null;
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

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public Integer getUnitsRequired()
    {
        return unitsRequired;
    }

    public void setUnitsRequired( Integer unitsRequired )
    {
        this.unitsRequired = unitsRequired;
    }

    public boolean isRequireAll()
    {
        return requireAll;
    }

    public void setRequireAll( boolean requireAll )
    {
        this.requireAll = requireAll;
    }

    public List<Course> getCourses()
    {
        return courses;
    }

    public void setCourses( List<Course> courses )
    {
        this.courses = courses;
    }

}
