package csns.web.editor;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class CalendarPropertyEditor extends PropertyEditorSupport {

    private DateFormat dateFormat;

    private static final Logger logger = LoggerFactory.getLogger( CalendarPropertyEditor.class );

    public CalendarPropertyEditor()
    {
        this( "MM/dd/yyyy HH:mm:ss" );
    }

    public CalendarPropertyEditor( String format )
    {
        this.dateFormat = new SimpleDateFormat( format );
    }

    public void setAsText( String text ) throws IllegalArgumentException
    {
        if( !StringUtils.hasText( text ) )
        {
            setValue( null );
            return;
        }

        try
        {
            Calendar value = Calendar.getInstance();
            value.setTime( dateFormat.parse( text ) );
            setValue( value );
        }
        catch( ParseException ex )
        {
            logger.warn( "Cannot parse date: " + text );
            throw new IllegalArgumentException( "Cannot parse date: " + text );
        }
    }

    public String getAsText()
    {
        Calendar value = (Calendar) getValue();
        return value != null ? dateFormat.format( value.getTime() ) : "";
    }

}
