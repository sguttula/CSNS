package csns.helper.highcharts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Chart {

    @JsonProperty("chart")
    Map<String, String> options;

    Title title;

    Axis xAxis, yAxis;

    List<Series> series;

    public Chart( String titleText )
    {
        this( titleText, null, null );
    }

    public Chart( String titleText, String xTitleText, String yTitleText )
    {
        options = new LinkedHashMap<String, String>();
        options.put( "type", "column" );

        title = new Title( titleText );
        xAxis = xTitleText == null ? new Axis() : new Axis( xTitleText );
        yAxis = yTitleText == null ? new Axis() : new Axis( yTitleText );
        series = new ArrayList<Series>();
    }

    public Map<String, String> getOptions()
    {
        return options;
    }

    public void setOptions( Map<String, String> options )
    {
        this.options = options;
    }

    public Title getTitle()
    {
        return title;
    }

    public void setTitle( Title title )
    {
        this.title = title;
    }

    public Axis getxAxis()
    {
        return xAxis;
    }

    public void setxAxis( Axis xAxis )
    {
        this.xAxis = xAxis;
    }

    public Axis getyAxis()
    {
        return yAxis;
    }

    public void setyAxis( Axis yAxis )
    {
        this.yAxis = yAxis;
    }

    public List<Series> getSeries()
    {
        return series;
    }

    public void setSeries( List<Series> series )
    {
        this.series = series;
    }

    public static void main( String args[] ) throws JsonProcessingException
    {
        Chart chart = new Chart( "Fruit Consumption", null, "Fruit Eaten" );

        String categories[] = { "Apples", "Bananas", "Oranges" };
        chart.getxAxis().setCategories( Arrays.asList( categories ) );

        Integer s1[] = { 1, 0, 4 };
        chart.getSeries().add( new Series( "Jane", Arrays.asList( s1 ) ) );
        Integer s2[] = { 5, 7, 3 };
        chart.getSeries().add( new Series( "John", Arrays.asList( s2 ) ) );

        ObjectMapper mapper = new ObjectMapper();
        System.out.println( mapper.writeValueAsString( chart ) );
    }

}
