package csns.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import csns.model.core.Resource;
import csns.model.core.ResourceType;
import csns.util.MyAntiSamy;

@Component
public class ResourceValidator implements Validator {

    @Autowired
    private MyAntiSamy antiSamy;

    @Override
    public boolean supports( Class<?> clazz )
    {
        return Resource.class.isAssignableFrom( clazz );
    }

    @Override
    public void validate( Object target, Errors errors )
    {
        ValidationUtils.rejectIfEmptyOrWhitespace( errors, "name",
            "error.field.required" );

        Resource resource = (Resource) target;
        if( resource.getType() != ResourceType.NONE )
        {
            switch( resource.getType() )
            {
                case TEXT:
                    if( !StringUtils.hasText( resource.getText() ) )
                        errors.rejectValue( "text", "error.field.required" );
                    else if( !antiSamy.validate( resource.getText() ) )
                        errors.rejectValue( "text", "error.html.invalid" );
                    break;

                case URL:
                    if( !StringUtils.hasText( resource.getUrl() ) )
                        errors.rejectValue( "url", "error.field.required" );
                    break;

                default:
                    // file upload is not validated.
            }
        }
    }

    public void validate( Resource resource, MultipartFile uploadedFile,
        Errors errors )
    {
        validate( resource, errors );

        if( resource.getType() == ResourceType.FILE
            && resource.getFile() == null
            && (uploadedFile == null || uploadedFile.isEmpty()) )
            errors.rejectValue( "file", "error.field.required" );
    }

}
