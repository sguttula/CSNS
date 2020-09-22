package csns.model.mailinglist;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import csns.model.academics.Department;
import csns.model.core.Subscribable;

@Entity
@Table(name = "mailinglists")
public class Mailinglist implements Subscribable, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Transient
    private long subscriptionCount;

    public Mailinglist()
    {
        subscriptionCount = 0;
    }

    @Override
    public String getType()
    {
        return "Mailing List";
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

    public Department getDepartment()
    {
        return department;
    }

    public void setDepartment( Department department )
    {
        this.department = department;
    }

    public long getSubscriptionCount()
    {
        return subscriptionCount;
    }

    public void setSubscriptionCount( long subscriptionCount )
    {
        this.subscriptionCount = subscriptionCount;
    }

}
