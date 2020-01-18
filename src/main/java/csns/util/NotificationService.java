package csns.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import csns.model.core.Subscribable;
import csns.model.core.Subscription;
import csns.model.core.User;
import csns.model.core.dao.SubscriptionDao;
import csns.model.core.dao.UserDao;
import csns.security.SecurityUtils;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Component
public class NotificationService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private SubscriptionDao subscriptionDao;

    @Autowired
    private Configuration freemarkerConfiguration;

    @Autowired
    private MassMailSender massMailSender;

    @Value("#{applicationProperties.url}")
    private String appUrl;

    @Value("#{applicationProperties.email}")
    private String appEmail;

    public void notifiy( Subscribable subscribable, String subject,
        String template, Map<String, Object> models, boolean notificationFlag )
        throws IOException, TemplateException
    {
        models.put( "appUrl", appUrl );
        models.put( "appEmail", appEmail );

        User user = userDao.getUser( SecurityUtils.getUser().getId() );
        List<Subscription> subscriptions = subscriptionDao
            .getSubscriptions( subscribable );
        List<String> addresses = new ArrayList<String>();
        for( Subscription subscription : subscriptions )
            if( !subscription.isNotificationSent()
                && subscription.getSubscriber() != user )
            {
                addresses.add( subscription.getSubscriber().getEmail() );
                if( subscription.isNotificationSent() != notificationFlag )
                {
                    subscription.setNotificationSent( notificationFlag );
                    subscriptionDao.saveSubscription( subscription );
                }
            }

        if( addresses.size() > 0 )
        {
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(
                freemarkerConfiguration.getTemplate( template ), models );
            SimpleMailMessage email = new SimpleMailMessage();
            email.setFrom( appEmail );
            email.setTo( appEmail );
            email.setSubject( subject );
            email.setText( text );
            massMailSender.send( email, addresses );
        }
    }

}
