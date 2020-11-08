package csns.model.wiki;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import csns.model.core.AbstractMessage;

@Entity
@Table(name = "wiki_revisions")
@AssociationOverride(name = "attachments",
    joinTable = @JoinTable(name = "wiki_revision_attachments",
        joinColumns = @JoinColumn(name = "revision_id"),
        inverseJoinColumns = @JoinColumn(name = "file_id")))
public class Revision extends AbstractMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "page_id", nullable = false)
    private Page page;

    @Column(name = "include_sidebar", nullable = false)
    private boolean includeSidebar;

    public Revision()
    {
        super();
        includeSidebar = false;
    }

    public Revision( Page page )
    {
        this();
        this.page = page;
    }

    public Revision clone()
    {
        Revision newRevision = new Revision();
        newRevision.subject = subject;
        newRevision.content = content;
        newRevision.includeSidebar = includeSidebar;
        newRevision.page = page;

        return newRevision;
    }

    public Page getPage()
    {
        return page;
    }

    public void setPage( Page page )
    {
        this.page = page;
    }

    public boolean isIncludeSidebar()
    {
        return includeSidebar;
    }

    public void setIncludeSidebar( boolean includeSidebar )
    {
        this.includeSidebar = includeSidebar;
    }

}
