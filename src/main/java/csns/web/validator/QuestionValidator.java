package csns.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import csns.model.academics.Assignment;
import csns.model.qa.ChoiceQuestion;
import csns.model.qa.RatingQuestion;
import csns.model.qa.TextQuestion;

@Component
public class QuestionValidator implements Validator {

    @Override
    public boolean supports( Class<?> clazz )
    {
        return Assignment.class.isAssignableFrom( clazz );
    }

    @Override
    public void validate( Object target, Errors errors )
    {
        ValidationUtils.rejectIfEmptyOrWhitespace( errors, "description",
            "error.field.required" );

        if( target instanceof ChoiceQuestion )
        {
            ChoiceQuestion question = (ChoiceQuestion) target;

            int minSelections = question.getMinSelections();
            int maxSelections = question.getMaxSelections();
            int numOfChoices = question.getNumOfChoices();

            if( maxSelections <= 0 ) maxSelections = 1;
            if( maxSelections > numOfChoices ) maxSelections = numOfChoices;
            if( minSelections < 0 ) minSelections = 0;
            if( minSelections > maxSelections ) minSelections = maxSelections;

            question.setMinSelections( minSelections );
            question.setMaxSelections( maxSelections );
        }

        if( target instanceof RatingQuestion )
        {
            RatingQuestion question = (RatingQuestion) target;
            if( question.getMinRating() > question.getMaxRating() )
                question.setMinRating( question.getMaxRating() );
        }

        if( target instanceof TextQuestion )
        {
            TextQuestion question = (TextQuestion) target;
            if( question.getTextLength() < 1 ) question.setTextLength( 1 );
        }
    }

}
