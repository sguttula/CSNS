package csns.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@Component
public class DepartmentFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger( DepartmentFilter.class );

    @Override
    protected void doFilterInternal( HttpServletRequest request,
        HttpServletResponse response, FilterChain filterChain )
        throws ServletException, IOException
    {
        String contextPath = request.getContextPath();
        String path = request.getRequestURI().substring( contextPath.length() );
        Cookie cookie = WebUtils.getCookie( request, "default-dept" );

        if( path.startsWith( "/department/" ) )
        {
            int beginIndex = "/department/".length();
            int endIndex = path.indexOf( "/", beginIndex );
            if( endIndex < 0 ) endIndex = path.length();
            String dept = path.substring( beginIndex, endIndex );
            request.setAttribute( "dept", dept );

            logger.debug( path + " -> " + dept );

            if( cookie == null )
            {
                cookie = new Cookie( "default-dept", dept );
                cookie.setPath( "/" );
                cookie.setMaxAge( 100000000 );
                response.addCookie( cookie );
            }
        }
        else
        {
            if( cookie != null )
                request.setAttribute( "dept", cookie.getValue() );
        }

        filterChain.doFilter( request, response );
    }

}
