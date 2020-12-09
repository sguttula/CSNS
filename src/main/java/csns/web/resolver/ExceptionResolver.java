package csns.web.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import csns.security.SecurityUtils;

public class ExceptionResolver extends SimpleMappingExceptionResolver {

    @Override
    public ModelAndView resolveException( HttpServletRequest request,
        HttpServletResponse response, Object handler, Exception exception )
    {
        if( !exception.getClass().getName().endsWith( "AccessDeniedException" ) )
        {
            String username = SecurityUtils.isAnonymous() ? "guest"
                : SecurityUtils.getUser().getUsername();
            logger.error( "Exception caused by " + username, exception );
        }

        return super.resolveException( request, response, handler, exception );
    }

}
