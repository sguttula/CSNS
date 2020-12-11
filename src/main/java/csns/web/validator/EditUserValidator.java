package csns.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import csns.model.core.User;

@Component
public class EditUserValidator extends AddUserValidator {

    @Override
    public void validate( Object target, Errors errors )
    {
        super.validate( target, errors );

        User user = (User) target;
        Long id = user.getId();

        ValidationUtils.rejectIfEmptyOrWhitespace( errors, "username",
            "error.field.required" );

        ValidationUtils.rejectIfEmptyOrWhitespace( errors, "primaryEmail",
            "error.field.required" );

        String username = user.getUsername();
        if( StringUtils.hasText( username ) )
        {
            User u = userDao.getUserByUsername( username );
            if( u != null && !u.getId().equals( id ) )
                errors.rejectValue( "username", "error.user.username.taken" );
        }

        String password1 = user.getPassword1();
        if( StringUtils.hasText( password1 ) )
        {
            if( password1.length() < 6 )
                errors.rejectValue( "password1", "error.user.password.short" );
            if( !password1.equals( user.getPassword2() ) )
                errors.rejectValue( "password2", "error.user.password.notmatch" );
        }
    }

}
