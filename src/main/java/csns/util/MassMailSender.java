package csns.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import csns.model.core.AbstractMessage;
import csns.model.core.Subscribable;

@Component
public class MassMailSender {

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    EmailUtils emailUtils;

    int maxRecipientsPerMessage = 30;

    private final static Logger logger = LoggerFactory.getLogger( MassMailSender.class );

    public MassMailSender()
    {
    }

    public void send( SimpleMailMessage email, List<String> addresses )
    {
        List<String> bccAddresses = new ArrayList<String>();
        for( int i = 0; i < addresses.size(); ++i )
        {
            if( !addresses.get( i ).endsWith( "@localhost" ) )
                bccAddresses.add( addresses.get( i ) );
            if( bccAddresses.size() >= maxRecipientsPerMessage
                || bccAddresses.size() > 0 && i == addresses.size() - 1 )
            {
                email.setBcc( bccAddresses.toArray( new String[0] ) );
                try
                {
                    mailSender.send( email );
                }
                catch( MailException e )
                {
                    logger.warn( e.getMessage() );
                }
                logger.debug( "sent email to "
                    + StringUtils.collectionToCommaDelimitedString( bccAddresses ) );
                bccAddresses.clear();
            }
        }
    }

    public void send( AbstractMessage message, Subscribable subscribable )
    {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject( message.getSubject() );
        email.setText( emailUtils.getText( message ) );
        email.setFrom( message.getAuthor().getPrimaryEmail() );
        email.setTo( emailUtils.getAppEmail() );
        send( email, emailUtils.getAddresses( subscribable ) );
    }

}
