package csns.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import csns.model.academics.Project;

@Component
public class ProjectValidator implements Validator {

    @Override
    public boolean supports( Class<?> clazz )
    {
        return Project.class.isAssignableFrom( clazz );
    }

    @Override
    public void validate( Object target, Errors errors )
    {
        ValidationUtils.rejectIfEmptyOrWhitespace( errors, "title",
            "error.field.required" );
    }

}
