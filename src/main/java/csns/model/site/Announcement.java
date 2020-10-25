package csns.model.site;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "site_announcements")
public class Announcement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Date date;

    private String content;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private Site site;

    public Announcement()
    {
        date = new Date();
    }

    public Announcement( Site site )
    {
        this();
        this.site = site;
    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate( Date date )
    {
        this.date = date;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent( String content )
    {
        this.content = content;
    }

    public Site getSite()
    {
        return site;
    }

    public void setSite( Site site )
    {
        this.site = site;
    }

}
