package csns.model.qa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("RATING")
public class RatingAnswer extends Answer {

    private static final long serialVersionUID = 1L;

    private Integer rating;

    public RatingAnswer()
    {
    }

    public RatingAnswer( RatingQuestion ratingQuestion )
    {
        super( ratingQuestion );
    }

    @Override
    public int check()
    {
        return 0;
    }

    @Override
    public String toString()
    {
        return rating != null ? rating.toString() : "";
    }

    public Integer getRating()
    {
        return rating;
    }

    public void setRating( Integer rating )
    {
        this.rating = rating;
    }

}
