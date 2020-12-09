package csns.web.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import csns.model.academics.Term;

@Component
@Scope("prototype")
public class TermPropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText( String text ) throws IllegalArgumentException
    {
        if( StringUtils.hasText( text ) )
            setValue( new Term( Integer.valueOf( text ) ) );
    }

    @Override
    public String getAsText()
    {
        Term term = (Term) getValue();
        return term != null ? "" + term.getCode() : "";
    }

}
