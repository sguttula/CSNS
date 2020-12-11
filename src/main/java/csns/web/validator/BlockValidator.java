package csns.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import csns.model.site.Block;

@Component
public class BlockValidator implements Validator {

    @Override
    public boolean supports( Class<?> clazz )
    {
        return Block.class.isAssignableFrom( clazz );
    }

    @Override
    public void validate( Object target, Errors errors )
    {
        ValidationUtils.rejectIfEmptyOrWhitespace( errors, "name",
            "error.field.required" );

        ValidationUtils.rejectIfEmptyOrWhitespace( errors, "type",
            "error.field.required" );
    }

}
