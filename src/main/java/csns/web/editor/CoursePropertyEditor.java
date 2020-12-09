package csns.web.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import csns.model.academics.Course;
import csns.model.academics.dao.CourseDao;

@Component
@Scope("prototype")
public class CoursePropertyEditor extends PropertyEditorSupport {

    @Autowired
    CourseDao courseDao;

    @Override
    public void setAsText( String text ) throws IllegalArgumentException
    {
        if( StringUtils.hasText( text ) )
            setValue( courseDao.getCourse( Long.valueOf( text ) ) );
    }

    @Override
    public String getAsText()
    {
        Course course = (Course) getValue();
        return course != null ? course.getId().toString() : "";
    }

}
