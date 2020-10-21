package csns.model.qa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Inheritance
@Table(name = "answers")
@DiscriminatorColumn(name = "answer_type")
public abstract class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "answer_section_id", nullable = false)
    protected AnswerSection section;

    @Column(name = "answer_index")
    protected int index;

    @ManyToOne
    @JoinColumn(name = "question_id")
    protected Question question;

    public Answer()
    {
    }

    public Answer( Question question )
    {
        this.question = question;
    }

    public abstract int check();

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public AnswerSection getSection()
    {
        return section;
    }

    public void setSection( AnswerSection section )
    {
        this.section = section;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex( int index )
    {
        this.index = index;
    }

    public Question getQuestion()
    {
        return question;
    }

    public void setQuestion( Question question )
    {
        this.question = question;
    }

}
