package csns.web.validator;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import csns.model.academics.Department;
import csns.model.academics.dao.DepartmentDao;

@Component
public class DepartmentValidator implements Validator {

    @Autowired
    DepartmentDao departmentDao;

    @Override
    public boolean supports( Class<?> clazz )
    {
        return Department.class.isAssignableFrom( clazz );
    }

    @Override
    public void validate( Object target, Errors errors )
    {
        Department department = (Department) target;
        Long id = department.getId();

        String name = department.getName();
        if( !StringUtils.hasText( name ) )
            errors.rejectValue( "name", "error.field.required" );
        else
        {
            Department d = departmentDao.getDepartmentByName( name );
            if( d != null && !d.getId().equals( id ) )
                errors.rejectValue( "name", "error.department.name.taken" );
        }

        String fullName = department.getFullName();
        if( !StringUtils.hasText( fullName ) )
            errors.rejectValue( "fullName", "error.field.required" );
        else
        {
            Department d = departmentDao.getDepartmentByFullName( fullName );
            if( d != null && !d.getId().equals( id ) )
                errors.rejectValue( "fullName", "error.department.name.taken" );
        }

        String abbreviation = department.getAbbreviation();
        if( !StringUtils.hasText( abbreviation ) )
            errors.rejectValue( "abbreviation", "error.field.required" );
        else if( !Pattern.matches( "[a-z]+", abbreviation ) )
            errors.rejectValue( "abbreviation",
                "error.department.abbreviation.invalid" );
        else
        {
            Department d = departmentDao.getDepartment( abbreviation );
            if( d != null && !d.getId().equals( id ) )
                errors.rejectValue( "abbreviation",
                    "error.department.abbreviation.taken" );
        }

        if( department.getAdministrators().size() == 0 )
            errors.rejectValue( "administrators", "error.field.required" );
    }

}
