package csns.web.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import csns.model.prereg.Section;
import csns.model.prereg.dao.SectionDao;

@Component
@Scope("prototype")
public class PreregSectionPropertyEditor extends PropertyEditorSupport {

    @Autowired
    SectionDao sectionDao;

    @Override
    public void setAsText( String text ) throws IllegalArgumentException
    {
        if( StringUtils.hasText( text ) )
            setValue( sectionDao.getSection( Long.valueOf( text ) ) );
        else
            setValue( null );
    }

    @Override
    public String getAsText()
    {
        Section section = (Section) getValue();
        return section != null ? section.getId().toString() : "";
    }

}
