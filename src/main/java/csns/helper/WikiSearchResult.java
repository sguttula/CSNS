package csns.helper;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class WikiSearchResult {

    @Id
    private Long id;

    private String path;

    private String subject;

    private String content;

    public WikiSearchResult()
    {
    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath( String path )
    {
        this.path = path;
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

}
