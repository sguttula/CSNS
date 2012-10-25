/*
 * This file is part of the CSNetwork Services (CSNS) project.
 * 
 * Copyright 2012, Chengyu Sun (csun@calstatela.edu).
 * 
 * CSNS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 * 
 * CSNS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for
 * more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with CSNS. If not, see http://www.gnu.org/licenses/agpl.html.
 */
package csns.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import csns.model.core.Subscribable;
import csns.model.core.Subscription;
import csns.model.core.User;
import csns.model.core.dao.SubscriptionDao;
import csns.model.forum.Forum;
import csns.model.forum.dao.ForumDao;
import csns.model.forum.dao.TopicDao;
import csns.security.SecurityUtils;
import csns.web.helper.ServiceResponse;

@Controller
public class SubscriptionController {

    @Autowired
    ForumDao forumDao;

    @Autowired
    TopicDao topicDao;

    @Autowired
    SubscriptionDao subscriptionDao;

    private static final Logger logger = LoggerFactory.getLogger( SubscriptionController.class );

    @RequestMapping("/subscription/forums")
    public String forumSubscriptions( ModelMap models )
    {
        User user = SecurityUtils.getUser();
        List<Subscription> subscriptions = subscriptionDao.getSubscriptions(
            user, Forum.class );

        List<Forum> departmentForums = new ArrayList<Forum>();
        List<Forum> courseForums = new ArrayList<Forum>();
        List<Forum> otherForums = new ArrayList<Forum>();
        for( Subscription subscription : subscriptions )
        {
            Forum forum = (Forum) subscription.getSubscribable();
            if( forum.getDepartment() != null )
                departmentForums.add( forum );
            else if( forum.getCourse() != null )
                courseForums.add( forum );
            else
                otherForums.add( forum );
        }

        models.put( "departmentForums", departmentForums );
        models.put( "courseForums", courseForums );
        models.put( "otherForums", otherForums );

        return "subscription/forums";
    }

    @RequestMapping("/subscription/{type}/subscribe")
    public String subscribe( @PathVariable String type, @RequestParam Long id,
        @RequestParam(required = false) String ajax, ModelMap models )
    {
        Subscribable subscribable = null;
        switch( type )
        {
            case "forum":
                subscribable = forumDao.getForum( id );
                break;
            case "topic":
                subscribable = topicDao.getTopic( id );
                break;
            default:
                logger.warn( "Unspported subscribable type: " + type );
        }

        User user = SecurityUtils.getUser();
        subscriptionDao.subscribe( subscribable, user );
        logger.info( user.getUsername() + " subscribed to "
            + subscribable.getType() + " " + subscribable.getName() + "." );

        if( ajax != null )
        {
            models.put( "result", new ServiceResponse() );
            return "jsonView";
        }
        else
        {
            String[] arguments = { subscribable.getType(),
                subscribable.getName() };
            models.put( "message", "status.subscribed" );
            models.put( "arguments", arguments );
            return "status";
        }
    }

    @RequestMapping("/subscription/{type}/unsubscribe")
    public String unsubscribe( @PathVariable String type,
        @RequestParam Long id, @RequestParam(required = false) String ajax,
        ModelMap models )
    {
        Subscribable subscribable = null;
        switch( type )
        {
            case "forum":
                subscribable = forumDao.getForum( id );
                break;
            case "topic":
                subscribable = topicDao.getTopic( id );
                break;
            default:
                logger.warn( "Unspported subscribable type: " + type );
        }

        User user = SecurityUtils.getUser();
        subscriptionDao.unsubscribe( subscribable, user );
        logger.info( user.getUsername() + " unsubscribed from "
            + subscribable.getType() + " " + subscribable.getName() + "." );

        if( ajax != null )
        {
            models.put( "result", new ServiceResponse() );
            return "jsonView";
        }
        else
        {
            String[] arguments = { subscribable.getType(),
                subscribable.getName() };
            models.put( "message", "status.unsubscribed" );
            models.put( "arguments", arguments );
            return "status";
        }
    }

}