package csns.model.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OrderBy;

@MappedSuperclass
public abstract class AbstractMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    protected Long id;

    @Column(nullable = false)
    protected String subject;

    @Column(nullable = false)
    protected String content;

    // Use @AssociationOverride to override this in subclass if necessary
    // (which is likely).
    @ManyToMany
    @JoinTable(name = "message_attachments",
        joinColumns = @JoinColumn(name = "message_id"),
        inverseJoinColumns = @JoinColumn(name = "file_id"))
    @OrderBy("name asc")
    protected List<File> attachments;

    @ManyToOne
    @JoinColumn(name = "author_id")
    protected User author;

    protected Date date;

    public AbstractMessage()
    {
        attachments = new ArrayList<File>();
    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject( String subject )
    {
        this.subject = subject;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent( String content )
    {
        this.content = content;
    }

    public List<File> getAttachments()
    {
        return attachments;
    }

    public void setAttachments( List<File> attachments )
    {
        this.attachments = attachments;
    }

    public User getAuthor()
    {
        return author;
    }

    public void setAuthor( User author )
    {
        this.author = author;
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
