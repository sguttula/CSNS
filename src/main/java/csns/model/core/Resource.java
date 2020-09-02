package csns.model.core;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "resources")
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private ResourceType type;

    private String text;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "file_id")
    private File file;

    private String url;

    @Column(name = "private", nullable = false)
    private boolean isPrivate;

    @Column(nullable = false)
    private boolean deleted;

    public Resource()
    {
        name = "";
        type = ResourceType.NONE;
        isPrivate = false;
        deleted = false;
    }

    public Resource( String name )
    {
        this();
        this.name = name;
    }

    public Resource( ResourceType type )
    {
        this();
        this.type = type;
    }

    public Resource( String name, ResourceType type )
    {
        this();
        this.name = name;
        this.type = type;
    }

    public Resource clone()
    {
        Resource resource = new Resource();

        resource.name = name;
        resource.type = type;
        resource.text = text;
        resource.url = url;
        if( file != null ) resource.file = file.clone();

        return resource;
    }

    public void delete()
    {
        deleted = true;
        if( file != null ) file.setDeleted( true );
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

    public ResourceType getType()
    {
        return type;
    }

    public void setType( ResourceType type )
    {
        this.type = type;
    }

    public String getText()
    {
        return text;
    }

    public void setText( String text )
    {
        this.text = text;
    }

    public File getFile()
    {
        return file;
    }

    public void setFile( File file )
    {
        this.file = file;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl( String url )
    {
        this.url = url;
    }

    public boolean isPrivate()
    {
        return isPrivate;
    }

    public void setPrivate( boolean isPrivate )
    {
        this.isPrivate = isPrivate;
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
