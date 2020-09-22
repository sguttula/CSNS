package csns.model.mailinglist.dao;

import java.util.List;

import csns.model.mailinglist.Mailinglist;
import csns.model.mailinglist.Message;

public interface MessageDao {

    Message getMessage( Long id );

    List<Message> getMessagess( Mailinglist mailinglist, int maxResults );

    List<Message> searchMessages( Mailinglist mailinglist, String text,
        int maxResults );

    Message saveMessage( Message message );

}
