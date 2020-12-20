package csns.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import csns.model.news.News;

@Component
public class NewsValidator implements Validator {

    @Override
    public boolean supports( Class<?> clazz )
    {
        return News.class.isAssignableFrom( clazz );
    }

    @Override
    public void validate( Object target, Errors errors )
    {
        ValidationUtils.rejectIfEmptyOrWhitespace( errors,
            "topic.firstPost.subject", "error.field.required" );
        ValidationUtils.rejectIfEmptyOrWhitespace( errors,
            "topic.firstPost.content", "error.field.required" );
    }

}
