package csns.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import csns.model.mailinglist.Mailinglist;
import csns.model.mailinglist.dao.MailinglistDao;
import csns.model.mailinglist.dao.MessageDao;

@Controller
public class MailinglistMessageController {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private MailinglistDao mailinglistDao;

    @RequestMapping("/department/{dept}/mailinglist/message/view")
    public String view( @RequestParam Long id, ModelMap models )
    {
        models.put( "message", messageDao.getMessage( id ) );
        return "mailinglist/message/view";
    }

    @RequestMapping("/department/{dept}/mailinglist/message/search")
    public String search( @RequestParam Long listId, @RequestParam String text,
        ModelMap models )
    {
        Mailinglist mailinglist = mailinglistDao.getMailinglist( listId );
        models.put( "mailinglist", mailinglist );
        models.put( "messages",
            messageDao.searchMessages( mailinglist, text, 40 ) );
        return "mailinglist/message/search";
    }

}
