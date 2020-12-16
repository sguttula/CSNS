package csns.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import csns.model.core.AbstractMessage;
import csns.util.MyAntiSamy;

@Component
public class MessageValidator implements Validator {

    @Autowired
    private MyAntiSamy antiSamy;

    @Override
    public boolean supports( Class<?> clazz )
    {
        return AbstractMessage.class.isAssignableFrom( clazz );
    }

    @Override
    public void validate( Object target, Errors errors )
    {
        AbstractMessage message = (AbstractMessage) target;

        ValidationUtils.rejectIfEmptyOrWhitespace( errors, "subject",
            "error.field.required" );

        String content = message.getContent();
        if( !StringUtils.hasText( content ) )
            errors.rejectValue( "content", "error.field.required" );
        else if( !antiSamy.validate( message.getContent() ) )
            errors.rejectValue( "content", "error.html.invalid" );
    }

}
