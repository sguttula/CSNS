package csns.web.validator;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import csns.helper.Email;
import csns.model.core.User;

@Component
public class EmailValidator extends MessageValidator {

    @Override
    public boolean supports( Class<?> clazz )
    {
        return Email.class.isAssignableFrom( clazz );
    }

    @Override
    public void validate( Object target, Errors errors )
    {
        super.validate( target, errors );

        List<User> recipients = ((Email) target).getRecipients();
        if( recipients == null || recipients.isEmpty() )
            errors.rejectValue( "recipients", "error.field.required" );
    }

}
