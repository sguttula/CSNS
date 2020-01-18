package csns.helper.highcharts;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Series {

    String name;

    List<? extends Number> data;

    Boolean showInLegend;

    String stack;

    public Series()
    {
    }

    public Series( String name, List<? extends Number> data )
    {
        this( name, data, null, null );
    }

    public Series( String name, List<? extends Number> data,
        Boolean showInLegend )
    {
        this( name, data, showInLegend, null );
    }

    public Series( String name, List<? extends Number> data,
        Boolean showInLegend, String stack )
    {
        this.name = name;
        this.data = data;
        this.showInLegend = showInLegend;
        this.stack = stack;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public List<? extends Number> getData()
    {
        return data;
    }

    public void setData( List<? extends Number> data )
    {
        this.data = data;
    }

    public Boolean getShowInLegend()
    {
        return showInLegend;
    }

    public void setShowInLegend( Boolean showInLegend )
    {
        this.showInLegend = showInLegend;
    }

    public String getStack()
    {
        return stack;
    }

    public void setStack( String stack )
    {
        this.stack = stack;
    }

}
