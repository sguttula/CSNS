package csns.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import csns.importer.MFTScoreImporter;

@Component
public class MFTScoreImporterValidator implements Validator {

    @Override
    public boolean supports( Class<?> clazz )
    {
        return MFTScoreImporter.class.isAssignableFrom( clazz );
    }

    @Override
    public void validate( Object target, Errors errors )
    {
        ValidationUtils.rejectIfEmptyOrWhitespace( errors, "date",
            "error.field.required" );
        ValidationUtils.rejectIfEmptyOrWhitespace( errors, "text",
            "error.field.required" );
    }

}
