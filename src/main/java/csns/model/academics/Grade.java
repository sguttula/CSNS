package csns.model.academics;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "grades")
public class Grade implements Serializable, Comparable<Grade> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String symbol;

    private Double value;

    private String description;

    public Grade()
    {
    }

    public int compareTo( Grade other )
    {
        if( other == null )
            throw new IllegalArgumentException( "Cannot compare to NULL" );

        Double v1 = getValue();
        Double v2 = other.getValue();

        if( v1 == null && v2 == null )
            return getSymbol().compareTo( other.getSymbol() );
        if( v1 != null && v2 == null ) return 1;
        if( v1 == null && v2 != null ) return -1;

        return v1.compareTo( v2 );
    }

    public boolean isPassingGrade()
    {
        return value != null && value >= 2 || symbol.equals( "CR" );
    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public void setSymbol( String symbol )
    {
        this.symbol = symbol;
    }

    public Double getValue()
    {
        return value;
    }

    public void setValue( Double value )
    {
        this.value = value;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

}
