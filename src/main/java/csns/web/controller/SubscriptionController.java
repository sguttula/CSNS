package csns.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import csns.model.core.Subscribable;
import csns.model.core.User;
import csns.model.core.dao.SubscriptionDao;
import csns.model.forum.dao.ForumDao;
import csns.model.forum.dao.TopicDao;
import csns.model.mailinglist.dao.MailinglistDao;
import csns.model.wiki.dao.PageDao;
import csns.security.SecurityUtils;

@Controller
public class SubscriptionController {

    @Autowired
    PageDao pageDao;

    @Autowired
    ForumDao forumDao;

    @Autowired
    TopicDao topicDao;

    @Autowired
    MailinglistDao mailinglistDao;

    @Autowired
    SubscriptionDao subscriptionDao;

    private static final Logger logger = LoggerFactory
        .getLogger( SubscriptionController.class );

    private Subscribable subscribe( String type, Long id )
    {
        Subscribable subscribable = null;
        switch( type )
        {
            case "page":
                subscribable = pageDao.getPage( id );
                break;
            case "forum":
                subscribable = forumDao.getForum( id );
                break;
            case "topic":
                subscribable = topicDao.getTopic( id );
                break;
            case "mailinglist":
                subscribable = mailinglistDao.getMailinglist( id );
                break;
            default:
                logger.error( "Unspported subscribable type: " + type );
                return null;
        }

        User user = SecurityUtils.getUser();
        subscriptionDao.subscribe( subscribable, user );

        logger.info( user.getUsername() + " subscribed to "
            + subscribable.getType() + " " + subscribable.getName() );

        return subscribable;
    }

    private Subscribable unsubscribe( String type, Long id )
    {
        Subscribable subscribable = null;
        switch( type )
        {
            case "page":
                subscribable = pageDao.getPage( id );
                break;
            case "forum":
                subscribable = forumDao.getForum( id );
                break;
            case "topic":
                subscribable = topicDao.getTopic( id );
                break;
            case "mailinglist":
                subscribable = mailinglistDao.getMailinglist( id );
                break;
            default:
                logger.error( "Unspported subscribable type: " + type );
                return null;
        }

        User user = SecurityUtils.getUser();
        subscriptionDao.unsubscribe( subscribable, user );

        logger.info( user.getUsername() + " unsubscribed from "
            + subscribable.getType() + " " + subscribable.getName() );

        return subscribable;
    }

    @RequestMapping(value = "/subscription/{type}/subscribe", params = "ajax")
    @ResponseBody
    public void ajaxSubscribe( @PathVariable String type,
        @RequestParam Long id )
    {
        subscribe( type, id );
    }

    @RequestMapping(value = "/subscription/{type}/unsubscribe", params = "ajax")
    @ResponseBody
    public void ajaxUnsubscribe( @PathVariable String type,
        @RequestParam Long id )
    {
        unsubscribe( type, id );
    }

    @RequestMapping("/subscription/{type}/subscribe")
    public String subscribe( @PathVariable String type, @RequestParam Long id,
        ModelMap models )
    {
        Subscribable subscribable = subscribe( type, id );
        String[] arguments = { subscribable.getType(), subscribable.getName() };
        models.put( "message", "status.subscribed" );
        models.put( "arguments", arguments );
        return "status";
    }

    @RequestMapping("/subscription/{type}/unsubscribe")
    public String unsubscribe( @PathVariable String type, @RequestParam Long id,
        ModelMap models )
    {
        Subscribable subscribable = unsubscribe( type, id );
        String[] arguments = { subscribable.getType(), subscribable.getName() };
        models.put( "message", "status.unsubscribed" );
        models.put( "arguments", arguments );
        return "status";
    }

}
