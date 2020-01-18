package csns.security;

import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;

import csns.model.core.User;

public class SecurityUtils {

    private static AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

    private static Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

    public static boolean isAnonymous()
    {
        return authenticationTrustResolver.isAnonymous(
            SecurityContextHolder.getContext().getAuthentication() );
    }

    public static boolean isAuthenticated()
    {
        return !isAnonymous();
    }

    public static User getUser()
    {
        return isAuthenticated() ? (User) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal() : null;
    }

    public static User createTemporaryAccount( String cin, String firstName,
        String lastName )
    {
        User user = new User();
        user.setCin( cin );
        user.setFirstName( firstName );
        user.setLastName( lastName );
        user.setUsername( cin );
        user.setPassword( passwordEncoder.encodePassword( cin, null ) );
        user.setPrimaryEmail( cin + "@localhost" );
        user.setTemporary( true );
        return user;
    }

}
