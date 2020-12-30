package csns.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

@Component
public class RegistrationValidator extends EditUserValidator {

    @Override
    public void validate( Object target, Errors errors )
    {
        super.validate( target, errors );

        ValidationUtils.rejectIfEmptyOrWhitespace( errors, "password1",
            "error.field.required" );

        ValidationUtils.rejectIfEmptyOrWhitespace( errors, "password2",
            "error.field.required" );
    }

}
