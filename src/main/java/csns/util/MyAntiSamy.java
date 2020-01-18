package csns.util;

import java.io.IOException;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class MyAntiSamy {

    private AntiSamy antiSamy;

    private Policy policy;

    private static final Logger logger = LoggerFactory.getLogger( MyAntiSamy.class );

    public MyAntiSamy( String policyFile ) throws PolicyException, IOException
    {
        antiSamy = new AntiSamy();
        policy = Policy.getInstance( new ClassPathResource( policyFile ).getInputStream() );
    }

    public CleanResults scan( String html )
    {
        CleanResults cleanResults = null;
        try
        {
            cleanResults = antiSamy.scan( html, policy );
            if( cleanResults.getNumberOfErrors() > 0 )
            {
                logger.warn( cleanResults.getNumberOfErrors()
                    + " violations found after scanning." );
                for( String errorMessage : cleanResults.getErrorMessages() )
                    logger.warn( errorMessage );
            }
        }
        catch( ScanException | PolicyException e )
        {
            logger.warn( e.getMessage(), e );
        }

        return cleanResults;
    }

    public String filter( String html )
    {
        CleanResults cleanResults = scan( html );
        return cleanResults != null ? cleanResults.getCleanHTML() : "";
    }

    public boolean validate( String html )
    {
        CleanResults cleanResults = scan( html );
        return cleanResults != null && cleanResults.getNumberOfErrors() == 0;
    }

}
