package csns.model.academics;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import csns.model.qa.QuestionSheet;

@Entity
@DiscriminatorValue("ONLINE")
public class OnlineAssignment extends Assignment {

    private static final long serialVersionUID = 1L;

    @OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST },
        fetch = FetchType.LAZY)
    @JoinColumn(name = "question_sheet_id", unique = true)
    private QuestionSheet questionSheet;

    public OnlineAssignment()
    {
        super();

        publishDate = null;
        availableAfterDueDate = false;
        questionSheet = new QuestionSheet();
    }

    @Override
    public OnlineAssignment clone()
    {
        OnlineAssignment assignment = new OnlineAssignment();

        assignment.name = name;
        assignment.alias = alias;
        assignment.totalPoints = totalPoints;
        assignment.dueDate = null;
        assignment.availableAfterDueDate = availableAfterDueDate;
        assignment.questionSheet = questionSheet.clone();

        return assignment;
    }

    @Override
    public boolean isOnline()
    {
        return true;
    }

    public void calcTotalPoints()
    {
        totalPoints = "" + questionSheet.getTotalPoints();
    }

    public QuestionSheet getQuestionSheet()
    {
        return questionSheet;
    }

    public void setQuestionSheet( QuestionSheet questionSheet )
    {
        this.questionSheet = questionSheet;
    }

}
