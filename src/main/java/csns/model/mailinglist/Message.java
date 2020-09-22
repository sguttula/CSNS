package csns.model.mailinglist;

import javax.persistence.AssociationOverride;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import csns.model.core.AbstractMessage;

@Entity
@Table(name = "mailinglist_messages")
@AssociationOverride(name = "attachments",
    joinTable = @JoinTable(name = "mailinglist_message_attachments",
        joinColumns = @JoinColumn(name = "message_id"),
        inverseJoinColumns = @JoinColumn(name = "file_id")))
public class Message extends AbstractMessage {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "mailinglist_id")
    private Mailinglist mailinglist;

    public Message()
    {
        super();
    }

    public Message( Mailinglist mailinglist )
    {
        super();
        this.mailinglist = mailinglist;
    }

    public Mailinglist getMailinglist()
    {
        return mailinglist;
    }

    public void setMailinglist( Mailinglist mailinglist )
    {
        this.mailinglist = mailinglist;
    }

}
