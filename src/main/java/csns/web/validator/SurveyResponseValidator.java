package csns.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import csns.model.survey.SurveyResponse;

@Component
public class SurveyResponseValidator implements Validator {

    @Autowired
    AnswerSheetValidator answerSheetValidator;

    @Override
    public boolean supports( Class<?> clazz )
    {
        return SurveyResponse.class.isAssignableFrom( clazz );
    }

    @Override
    public void validate( Object target, Errors errors )
    {
        SurveyResponse response = (SurveyResponse) target;
        errors.pushNestedPath( "answerSheet" );
        ValidationUtils.invokeValidator( answerSheetValidator,
            response.getAnswerSheet(), errors );
    }

    public void validate( SurveyResponse response, int sectionIndex,
        Errors errors )
    {
       // errors.pushNestedPath( "answerSheet" );
        answerSheetValidator.validateSection( response.getAnswerSheet(),
            sectionIndex, errors );
    }

}
