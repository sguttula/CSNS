package csns.model.core;

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
import javax.persistence.OrderBy;
import javax.persistence.Table;

import csns.model.academics.Department;

@Entity
@Table(name = "groups")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Date date;

    @OneToMany(mappedBy = "group")
    @OrderBy("id desc")
    private List<Member> members;

    public Group()
    {
        date = new Date();
        members = new ArrayList<Member>();
    }

    public Group( Department department )
    {
        this();
        this.department = department;
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

    public Date getDate()
    {
        return date;
    }

    public void setDate( Date date )
    {
        this.date = date;
    }

    public List<Member> getMembers()
    {
        return members;
    }

    public void setMembers( List<Member> members )
    {
        this.members = members;
    }

}
