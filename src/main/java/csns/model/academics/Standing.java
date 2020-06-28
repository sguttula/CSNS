package csns.model.academics;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "standings")
public class Standing implements Serializable, Comparable<Standing> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String symbol;

    private String name;

    private String description;

    /** The mailing lists a student with this standing should subscribe. */
    @ElementCollection
    @CollectionTable(name = "standing_mailinglists",
        joinColumns = @JoinColumn(name = "standing_id"))
    @Column(name = "mailinglist")
    private Set<String> mailinglists;

    public Standing()
    {
        mailinglists = new HashSet<String>();
    }

    @Override
    public int compareTo( Standing standing )
    {
        if( standing == null )
            throw new IllegalArgumentException( "Cannot compare to NULL." );

        return id.compareTo( standing.getId() );
    }

    @Override
    public String toString()
    {
        return symbol;
    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public void setSymbol( String symbol )
    {
        this.symbol = symbol;
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

    public Set<String> getMailinglists()
    {
        return mailinglists;
    }

    public void setMailinglists( Set<String> mailinglists )
    {
        this.mailinglists = mailinglists;
    }

}
