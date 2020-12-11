package csns.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import csns.util.RomanNumberFormat;

public class RomanNumberTag extends SimpleTagSupport {

    private int value;

    public RomanNumberTag()
    {
        value = 1;
    }

    public void setValue( int value )
    {
        this.value = value > 0 ? value : 1;
    }

    @Override
    public void doTag() throws IOException
    {
        JspWriter out = getJspContext().getOut();
        out.print( (new RomanNumberFormat()).format( value ) );
    }

}
