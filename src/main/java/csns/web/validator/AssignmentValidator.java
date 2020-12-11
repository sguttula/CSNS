package csns.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import csns.model.academics.Assignment;
import csns.model.core.ResourceType;

@Component
public class AssignmentValidator implements Validator {

    @Autowired
    ResourceValidator resourceValidator;

    @Override
    public boolean supports( Class<?> clazz )
    {
        return Assignment.class.isAssignableFrom( clazz );
    }

    @Override
    public void validate( Object target, Errors errors )
    {
        ValidationUtils.rejectIfEmptyOrWhitespace( errors, "name",
            "error.field.required" );

        Assignment assignment = (Assignment) target;
        if( !StringUtils.hasText( assignment.getAlias() ) )
            assignment.setAlias( assignment.getName() );
    }

    public void validate( Assignment assignment, MultipartFile uploadedFile,
        Errors errors )
    {
        validate( assignment, errors );

        if( !assignment.isOnline()
            && assignment.getDescription().getType() == ResourceType.FILE
            && assignment.getDescription().getFile() == null
            && (uploadedFile == null || uploadedFile.isEmpty()) )
            errors.rejectValue( "description.file", "error.field.required" );
    }

}
