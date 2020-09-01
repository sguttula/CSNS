package csns.model.assessment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

@Entity
@Table(name = "rubric_indicators")
public class RubricIndicator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @ElementCollection
    @CollectionTable(name = "rubric_indicator_criteria",
        joinColumns = @JoinColumn(name = "indicator_id"))
    @Column(name = "criterion")
    @OrderColumn(name = "criterion_index")
    private List<String> criteria;

    public RubricIndicator()
    {
        criteria = new ArrayList<String>();
    }

    public RubricIndicator clone()
    {
        RubricIndicator newIndicator = new RubricIndicator();
        newIndicator.name = name;
        newIndicator.criteria.addAll( criteria );
        return newIndicator;
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

    public List<String> getCriteria()
    {
        return criteria;
    }

    public void setCriteria( List<String> criteria )
    {
        this.criteria = criteria;
    }

}
