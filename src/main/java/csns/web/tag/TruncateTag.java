package csns.web.tag;

import java.io.IOException;

import javax.servlet.jsp.tagext.SimpleTagSupport;

public class TruncateTag extends SimpleTagSupport {

    private int length;

    private String value;

    public TruncateTag()
    {
        length = 80;
    }

    public void setLength( int length )
    {
        if( length > 0 ) this.length = length;
    }

    public void setValue( String value )
    {
        this.value = value;
    }

    @Override
    public void doTag() throws IOException
    {
        if( value.length() > length )
            value = value.substring( 0, length - 3 ) + "...";

        getJspContext().getOut().println( value );
    }

}
