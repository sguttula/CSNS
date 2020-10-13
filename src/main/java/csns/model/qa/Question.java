package csns.model.qa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Inheritance
@Table(name = "questions")
@DiscriminatorColumn(name = "question_type")
public abstract class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    protected Long id;

    protected String description;

    @Column(name = "point_value", nullable = false)
    protected int pointValue;

    @OneToMany(mappedBy = "question")
    @OrderBy("id asc")
    protected List<Answer> answers;

    public Question()
    {
        pointValue = 1;
        answers = new ArrayList<Answer>();
    }

    public int getNumOfAnswers()
    {
        return answers.size();
    }

    public abstract String getType();

    public abstract Answer createAnswer();

    public abstract Question clone();

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public int getPointValue()
    {
        return pointValue;
    }

    public void setPointValue( int pointValue )
    {
        this.pointValue = pointValue;
    }

    public List<Answer> getAnswers()
    {
        return answers;
    }

    public void setAnswers( List<Answer> answers )
    {
        this.answers = answers;
    }

}
