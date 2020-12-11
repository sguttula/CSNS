package csns.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import csns.model.site.Announcement;

@Component
public class AnnouncementValidator implements Validator {

    @Override
    public boolean supports( Class<?> clazz )
    {
        return Announcement.class.isAssignableFrom( clazz );
    }

    @Override
    public void validate( Object target, Errors errors )
    {
        ValidationUtils.rejectIfEmptyOrWhitespace( errors, "content",
            "error.field.required" );
    }

}
