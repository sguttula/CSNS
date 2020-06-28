package csns.model.academics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import csns.model.academics.Department;
import csns.model.core.User;

@Entity
@Table(name = "programs")
public class Program implements Serializable {

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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "program_id")
    @OrderColumn(name = "block_index")
    private List<ProgramBlock> blocks;

    @Column(name = "publish_date")
    private Calendar publishDate;

    @ManyToOne
    @JoinColumn(name = "published_by")
    private User publishedBy;

    @Column(nullable = false)
    private boolean obsolete;

    public Program()
    {
        blocks = new ArrayList<ProgramBlock>();
        obsolete = false;
    }

    public Program( Department department )
    {
        this();
        this.department = department;
    }

    public Program clone()
    {
        Program program = new Program();

        program.department = department;
        program.name = "Copy of " + name;
        program.description = description;
        for( ProgramBlock block : blocks )
            program.blocks.add( block.clone() );

        return program;
    }

    public boolean isPublished()
    {
        return publishDate != null
            && Calendar.getInstance().after( publishDate );
    }

    public ProgramBlock removeBlock( Long blockId )
    {
        for( int i = 0; i < blocks.size(); ++i )
            if( blocks.get( i ).getId().equals( blockId ) )
                return blocks.remove( i );

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

    public List<ProgramBlock> getBlocks()
    {
        return blocks;
    }

    public void setBlocks( List<ProgramBlock> blocks )
    {
        this.blocks = blocks;
    }

    public Calendar getPublishDate()
    {
        return publishDate;
    }

    public void setPublishDate( Calendar publishDate )
    {
        this.publishDate = publishDate;
    }

    public User getPublishedBy()
    {
        return publishedBy;
    }

    public void setPublishedBy( User publishedBy )
    {
        this.publishedBy = publishedBy;
    }

    public boolean isObsolete()
    {
        return obsolete;
    }

    public void setObsolete( boolean obsolete )
    {
        this.obsolete = obsolete;
    }

}
