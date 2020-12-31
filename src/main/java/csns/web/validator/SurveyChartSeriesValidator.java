package csns.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import csns.model.survey.SurveyChartSeries;

@Component
public class SurveyChartSeriesValidator implements Validator {

    @Override
    public boolean supports( Class<?> clazz )
    {
        return SurveyChartSeries.class.isAssignableFrom( clazz );
    }

    @Override
    public void validate( Object target, Errors errors )
    {
        ValidationUtils.rejectIfEmptyOrWhitespace( errors, "name",
            "error.field.required" );
    }

}
