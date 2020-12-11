package csns.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import csns.model.core.User;
import csns.model.core.dao.UserDao;

@Component
public class AddUserValidator implements Validator {

    @Autowired
    UserDao userDao;

    @Override
    public boolean supports( Class<?> clazz )
    {
        return User.class.isAssignableFrom( clazz );
    }

    @Override
    public void validate( Object target, Errors errors )
    {
        User user = (User) target;
        Long id = user.getId();

        ValidationUtils.rejectIfEmptyOrWhitespace( errors, "firstName",
            "error.field.required" );

        ValidationUtils.rejectIfEmptyOrWhitespace( errors, "lastName",
            "error.field.required" );

        ValidationUtils.rejectIfEmptyOrWhitespace( errors, "cin",
            "error.field.required" );

        String cin = user.getCin();
        if( StringUtils.hasText( cin ) )
        {
            User u = userDao.getUserByCin( cin );
            if( u != null && !u.getId().equals( id ) )
                errors.rejectValue( "cin", "error.user.cin.taken" );
        }

        String primaryEmail = user.getPrimaryEmail();
        if( StringUtils.hasText( primaryEmail ) )
        {
            User u = userDao.getUserByEmail( primaryEmail );
            if( u != null && !u.getId().equals( id ) )
                errors.rejectValue( "primaryEmail", "error.user.email.taken" );
        }
    }

}
