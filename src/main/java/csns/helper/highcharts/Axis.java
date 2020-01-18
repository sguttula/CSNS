package csns.helper.highcharts;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Axis {

    Title title;

    List<String> categories;

    Integer min, max;

    public Axis()
    {
    }

    public Axis( String titleText )
    {
        title = new Title( titleText );
    }

    public void setCategories( int beginYear, int endYear )
    {
        categories = new ArrayList<String>();
        for( int i = beginYear; i <= endYear; ++i )
            categories.add( Integer.valueOf( i ).toString() );
    }

    public void setTitleText( String titleText )
    {
        title = new Title( titleText );
    }

    public Title getTitle()
    {
        return title;
    }

    public void setTitle( Title title )
    {
        this.title = title;
    }

    public List<String> getCategories()
    {
        return categories;
    }

    public void setCategories( List<String> categories )
    {
        this.categories = categories;
    }

    public Integer getMin()
    {
        return min;
    }

    public void setMin( Integer min )
    {
        this.min = min;
    }

    public Integer getMax()
    {
        return max;
    }

    public void setMax( Integer max )
    {
        this.max = max;
    }

}
