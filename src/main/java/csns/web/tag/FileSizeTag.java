package csns.web.tag;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.jsp.tagext.SimpleTagSupport;

public class FileSizeTag extends SimpleTagSupport {

    private double value;

    @Override
    public void doTag() throws IOException
    {
        String unit = "B";

        if( value >= 1024 )
        {
            value /= 1024;
            unit = "KB";
        }

        if( value >= 1024 )
        {
            value /= 1024;
            unit = "MB";
        }

        if( value >= 1024 )
        {
            value /= 1024;
            unit = "GB";
        }

        getJspContext().getOut().print(
            (new DecimalFormat( "#.#" ).format( value )) + " " + unit );
    }

    public void setValue( double value )
    {
        this.value = value;
    }

}
