package csns.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import csns.security.SecurityUtils;

@Component
public class RegistrationFilter extends OncePerRequestFilter {

    private boolean isPassThrough( String path )
    {
        return path.startsWith( "/img/" ) || path.startsWith( "/css/" )
            || path.startsWith( "/js/" ) || path.equals( "/favicon.ico" )
            || path.equals( "/register" );
    }

    @Override
    protected void doFilterInternal( HttpServletRequest request,
        HttpServletResponse response, FilterChain filterChain )
        throws ServletException, IOException
    {
        String contextPath = request.getContextPath();
        String path = request.getRequestURI().substring( contextPath.length() );

        if( SecurityUtils.isAuthenticated()
            && SecurityUtils.getUser().isTemporary() && !isPassThrough( path ) )
        {
            response.sendRedirect( contextPath + "/register" );
            return;
        }

        filterChain.doFilter( request, response );
    }

}
