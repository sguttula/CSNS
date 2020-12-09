package csns.web.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import csns.model.assessment.Rubric;
import csns.model.assessment.dao.RubricDao;

@Component
@Scope("prototype")
public class RubricPropertyEditor extends PropertyEditorSupport {

    @Autowired
    private RubricDao rubricDao;

    @Override
    public void setAsText( String text ) throws IllegalArgumentException
    {
        if( StringUtils.hasText( text ) )
            setValue( rubricDao.getRubric( Long.valueOf( text ) ) );
    }

    @Override
    public String getAsText()
    {
        Rubric rubric = (Rubric) getValue();
        return rubric != null ? rubric.getId().toString() : "";
    }

}
