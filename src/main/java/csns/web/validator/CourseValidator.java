package csns.web.validator;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import csns.model.academics.Course;
import csns.model.academics.dao.CourseDao;

@Component
public class CourseValidator implements Validator {

    @Autowired
    CourseDao courseDao;

    @Override
    public boolean supports( Class<?> clazz )
    {
        return Course.class.isAssignableFrom( clazz );
    }

    @Override
    public void validate( Object target, Errors errors )
    {
        Course course = (Course) target;

        ValidationUtils.rejectIfEmptyOrWhitespace( errors, "name",
            "error.field.required" );

        String code = course.getCode();
        if( !StringUtils.hasText( code ) )
            errors.rejectValue( "code", "error.field.required" );
        else if( !Pattern.matches( "[A-Z]+\\d+[A-Z]{0,2}", code ) )
            errors.rejectValue( "code", "error.course.code.invalid" );
        else
        {
            Course c = courseDao.getCourse( code );
            if( c != null && !c.getId().equals( course.getId() ) )
                errors.rejectValue( "code", "error.course.exists" );
        }

        if( course.getUnits() < 0 )
            errors.rejectValue( "units", "error.course.units.invalid" );

        if( course.getUnitFactor() <= 0 || course.getUnitFactor() > 1 ) errors
            .rejectValue( "unitFactor", "error.course.unitfactor.invalid" );
    }

}
