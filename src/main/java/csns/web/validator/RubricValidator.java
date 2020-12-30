package csns.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import csns.model.assessment.Rubric;

@Component
public class RubricValidator implements Validator {

    @Override
    public boolean supports( Class<?> clazz )
    {
        return Rubric.class.isAssignableFrom( clazz );
    }

    @Override
    public void validate( Object target, Errors errors )
    {
        ValidationUtils.rejectIfEmptyOrWhitespace( errors, "name",
            "error.field.required" );

        int scale = ((Rubric) target).getScale();
        if( scale < 2 || scale > 6 )
            errors.rejectValue( "scale", "error.rubric.scale.invalid" );
    }

}
