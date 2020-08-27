package csns.model.assessment;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class MFTDistributionEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    private int value;

    private int percentile;

    public MFTDistributionEntry()
    {
    }

    public MFTDistributionEntry( int value, int percentile )
    {
        this.value = value;
        this.percentile = percentile;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue( int value )
    {
        this.value = value;
    }

    public int getPercentile()
    {
        return percentile;
    }

    public void setPercentile( int percentile )
    {
        this.percentile = percentile;
    }

}
