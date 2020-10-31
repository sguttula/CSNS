package csns.model.survey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import csns.helper.highcharts.Chart;
import csns.model.academics.Department;
import csns.model.core.User;

@Entity
@Table(name = "survey_charts")
public class SurveyChart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "x_label")
    private String xLabel;

    @ElementCollection
    @CollectionTable(name = "survey_chart_xcoordinates",
        joinColumns = @JoinColumn(name = "chart_id"))
    @Column(name = "coordinate")
    @OrderColumn(name = "coordinate_order")
    private List<String> xCoordinates;

    @Column(name = "y_label")
    private String yLabel;

    @Column(name = "y_min")
    private Integer yMin;

    @Column(name = "y_max")
    private Integer yMax;

    @OneToMany(mappedBy = "chart")
    @OrderBy("name asc")
    private List<SurveyChartSeries> series;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    private Date date;

    private boolean deleted;

    public SurveyChart()
    {
        xCoordinates = new ArrayList<String>();
        series = new ArrayList<SurveyChartSeries>();
        date = new Date();
        deleted = false;
    }

    public Chart getHighchart()
    {
        Chart chart = new Chart( name, xLabel, yLabel );
        chart.getxAxis().setCategories( xCoordinates );

        boolean showInLegend = series.size() > 1;
        for( SurveyChartSeries serie : series )
            chart.getSeries().add( serie.getHighchartSeries( showInLegend ) );

        if( yMin != null ) chart.getyAxis().setMin( yMin );
        if( yMax != null ) chart.getyAxis().setMax( yMax );

        return chart;
    }

    public boolean setValues()
    {
        boolean valuesSet = false;
        for( SurveyChartSeries serie : series )
            if( serie.setValues() ) valuesSet = true;
        return valuesSet;
    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getxLabel()
    {
        return xLabel;
    }

    public void setxLabel( String xLabel )
    {
        this.xLabel = xLabel;
    }

    public List<String> getxCoordinates()
    {
        return xCoordinates;
    }

    public void setxCoordinates( List<String> xCoordinates )
    {
        this.xCoordinates = xCoordinates;
    }

    public String getyLabel()
    {
        return yLabel;
    }

    public void setyLabel( String yLabel )
    {
        this.yLabel = yLabel;
    }

    public Integer getyMin()
    {
        return yMin;
    }

    public void setyMin( Integer yMin )
    {
        this.yMin = yMin;
    }

    public Integer getyMax()
    {
        return yMax;
    }

    public void setyMax( Integer yMax )
    {
        this.yMax = yMax;
    }

    public List<SurveyChartSeries> getSeries()
    {
        return series;
    }

    public void setSeries( List<SurveyChartSeries> series )
    {
        this.series = series;
    }

    public User getAuthor()
    {
        return author;
    }

    public void setAuthor( User author )
    {
        this.author = author;
    }

    public Department getDepartment()
    {
        return department;
    }

    public void setDepartment( Department department )
    {
        this.department = department;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate( Date date )
    {
        this.date = date;
    }

    public boolean isDeleted()
    {
        return deleted;
    }

    public void setDeleted( boolean deleted )
    {
        this.deleted = deleted;
    }

}
