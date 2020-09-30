package csns.model.qa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("RATING")
public class RatingQuestion extends Question {

    private static final long serialVersionUID = 1L;

    @Column(name = "min_rating")
    protected int minRating;

    @Column(name = "max_rating")
    protected int maxRating;

    public RatingQuestion()
    {
        minRating = 1;
        maxRating = 5;
    }

    @Override
    public String getType()
    {
        return "RATING";
    }

    @Override
    public Answer createAnswer()
    {
        RatingAnswer answer = new RatingAnswer( this );
        answers.add( answer );
        return answer;
    }

    @Override
    public Question clone()
    {
        RatingQuestion newQuestion = new RatingQuestion();

        newQuestion.description = description;
        newQuestion.pointValue = pointValue;
        newQuestion.minRating = minRating;
        newQuestion.maxRating = maxRating;

        return newQuestion;
    }

    public List<Integer> getRatingSelections()
    {
        List<Integer> ratingSelections = new ArrayList<Integer>();
        for( int i = minRating; i <= maxRating; ++i )
            ratingSelections.add( 0 );

        for( Answer answer : answers )
        {
            Integer rating = ((RatingAnswer) answer).getRating();
            if( rating != null )
            {
                int selection = rating - minRating;
                ratingSelections.set( selection,
                    ratingSelections.get( selection ) + 1 );
            }
        }

        return ratingSelections;
    }

    public Map<String, Number> getRatingStats()
    {
        if( answers.size() == 0 ) return null;

        List<Integer> ratings = new ArrayList<Integer>();
        for( Answer answer : answers )
        {
            Integer rating = ((RatingAnswer) answer).getRating();
            if( rating != null ) ratings.add( rating );
        }
        if( ratings.size() == 0 ) return null;

        Collections.sort( ratings );

        double average = 0.0;
        for( Integer rating : ratings )
            average += rating;
        average = average / ratings.size();

        double median = ratings.get( ratings.size() / 2 );
        if( ratings.size() % 2 == 0 )
            median = (median + ratings.get( ratings.size() / 2 - 1 )) / 2;

        Map<String, Number> ratingStats = new HashMap<String, Number>();
        ratingStats.put( "min", ratings.get( 0 ) );
        ratingStats.put( "max", ratings.get( ratings.size() - 1 ) );
        ratingStats.put( "average", average );
        ratingStats.put( "median", median );

        return ratingStats;
    }

    public int getMinRating()
    {
        return minRating;
    }

    public void setMinRating( int minRating )
    {
        this.minRating = minRating;
    }

    public int getMaxRating()
    {
        return maxRating;
    }

    public void setMaxRating( int maxRating )
    {
        this.maxRating = maxRating;
    }

}
