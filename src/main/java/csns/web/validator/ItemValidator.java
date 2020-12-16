package csns.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import csns.model.core.Resource;
import csns.model.core.ResourceType;
import csns.model.site.Item;

@Component
public class ItemValidator implements Validator {

    @Autowired
    ResourceValidator resourceValidator;

    @Override
    public boolean supports( Class<?> clazz )
    {
        return Item.class.isAssignableFrom( clazz );
    }

    @Override
    public void validate( Object target, Errors errors )
    {
        Item item = (Item) target;

        errors.pushNestedPath( "resource" );
        ValidationUtils.invokeValidator( resourceValidator, item.getResource(),
            errors );
        errors.popNestedPath();
    }

    /**
     * Controllers should call this method instead of validate(Object,Errors) so
     * the file field can be validated as well.
     */
    public void validate( Item item, MultipartFile uploadedFile, Errors errors )
    {
        validate( item, errors );

        Resource resource = item.getResource();
        if( resource.getType() == ResourceType.FILE
            && resource.getFile() == null
            && (uploadedFile == null || uploadedFile.isEmpty()) )
            errors.rejectValue( "resource.file", "error.field.required" );
    }

}
