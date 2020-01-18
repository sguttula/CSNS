package csns.helper;

import java.util.ArrayList;
import java.util.List;

import csns.model.core.AbstractMessage;
import csns.model.core.User;

public class Email extends AbstractMessage {

    private static final long serialVersionUID = 1L;

    private List<User> recipients;

    private boolean useSecondaryEmail;

    public Email()
    {
        recipients = new ArrayList<User>();
        useSecondaryEmail = false;
    }

    public void addRecipient( User user )
    {
        recipients.add( user );
    }

    public List<User> getRecipients()
    {
        return recipients;
    }

    public void setRecipients( List<User> recipients )
    {
        this.recipients = recipients;
    }

    public boolean isUseSecondaryEmail()
    {
        return useSecondaryEmail;
    }

    public void setUseSecondaryEmail( boolean useSecondaryEmail )
    {
        this.useSecondaryEmail = useSecondaryEmail;
    }

}
