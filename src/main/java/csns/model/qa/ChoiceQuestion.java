package csns.model.qa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OrderColumn;

@Entity
@DiscriminatorValue("CHOICE")
public class ChoiceQuestion extends Question {

    private static final long serialVersionUID = 1L;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "question_choices",
        joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "choice")
    @OrderColumn(name = "choice_index")
    protected List<String> choices;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "question_correct_selections",
        joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "selection")
    protected Set<Integer> correctSelections;

    @Column(name = "min_selections")
    protected int minSelections;

    @Column(name = "max_selections")
    protected int maxSelections;

    public ChoiceQuestion()
    {
        choices = new ArrayList<String>();
        for( int i = 0; i < 4; ++i )
            choices.add( "" );
        correctSelections = new HashSet<Integer>();
        minSelections = 0;
        maxSelections = 4;
    }

    @Override
    public String getType()
    {
        return "CHOICE";
    }

    @Override
    public Answer createAnswer()
    {
        ChoiceAnswer answer = new ChoiceAnswer( this );
        answers.add( answer );
        return answer;
    }

    @Override
    public Question clone()
    {
        ChoiceQuestion newQuestion = new ChoiceQuestion();

        newQuestion.description = description;
        newQuestion.pointValue = pointValue;
        newQuestion.minSelections = minSelections;
        newQuestion.maxSelections = maxSelections;
        newQuestion.choices.clear();
        newQuestion.choices.addAll( choices );
        newQuestion.correctSelections.addAll( correctSelections );

        return newQuestion;
    }

    public boolean isSingleSelection()
    {
        return maxSelections == 1;
    }

    public List<Integer> getChoiceSelections()
    {
        List<Integer> choiceSelections = new ArrayList<Integer>();
        for( int i = 0; i < choices.size(); ++i )
            choiceSelections.add( 0 );

        for( Answer answer : answers )
            for( Integer selection : ((ChoiceAnswer) answer).getSelections() )
                choiceSelections.set( selection,
                    choiceSelections.get( selection ) + 1 );

        return choiceSelections;
    }

    public int getNumOfChoices()
    {
        return choices.size();
    }

    public void setNumOfChoices( int numOfChoices )
    {
        if( numOfChoices <= 0 ) numOfChoices = 1;

        if( numOfChoices > choices.size() )
            for( int i = choices.size(); i < numOfChoices; ++i )
                choices.add( "" );

        if( numOfChoices < choices.size() )
            for( int i = choices.size(); i > numOfChoices; --i )
            {
                choices.remove( i - 1 );
                correctSelections.remove( i - 1 );
            }
    }

    public List<Integer> getAnswerStats()
    {
        List<Integer> answerStats = new ArrayList<Integer>();
        for( int i = 0; i < choices.size(); ++i )
            answerStats.add( 0 );

        for( Answer answer : answers )
            for( Integer selection : ((ChoiceAnswer) answer).getSelections() )
                answerStats.set( selection, answerStats.get( selection ) + 1 );

        return answerStats;
    }

    public List<String> getChoices()
    {
        return choices;
    }

    public void setChoices( List<String> choices )
    {
        this.choices = choices;
    }

    public Set<Integer> getCorrectSelections()
    {
        return correctSelections;
    }

    public void setCorrectSelections( Set<Integer> correctSelections )
    {
        this.correctSelections = correctSelections;
    }

    public int getMinSelections()
    {
        return minSelections;
    }

    public void setMinSelections( int minSelections )
    {
        this.minSelections = minSelections;
    }

    public int getMaxSelections()
    {
        return maxSelections;
    }

    public void setMaxSelections( int maxSelections )
    {
        this.maxSelections = maxSelections;
    }

}
