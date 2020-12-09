package csns.web.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import csns.model.academics.Standing;
import csns.model.academics.dao.StandingDao;

@Component
@Scope("prototype")
public class StandingPropertyEditor extends PropertyEditorSupport {

    @Autowired
    StandingDao standingDao;

    @Override
    public void setAsText( String text ) throws IllegalArgumentException
    {
        if( StringUtils.hasText( text ) )
            setValue( standingDao.getStanding( Long.valueOf( text ) ) );
    }

    @Override
    public String getAsText()
    {
        Standing standing = (Standing) getValue();
        return standing != null ? standing.getId().toString() : "";
    }

}
