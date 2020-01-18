package csns.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.util.StringUtils;

public class AuthenticationFailureHandler extends
    SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure( HttpServletRequest request,
        HttpServletResponse response, AuthenticationException exception )
        throws IOException, ServletException
    {
        String username = request.getParameter( "j_username" );
        if( StringUtils.hasText( username ) )
            logger.info( "Failed login attempt of " + username + " from "
                + request.getRemoteAddr() );

        super.onAuthenticationFailure( request, response, exception );
    }

}
