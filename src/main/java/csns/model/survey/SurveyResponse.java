package csns.model.survey;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import csns.model.qa.AnswerSheet;

@Entity
@Table(name = "survey_responses")
public class SurveyResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;

    @OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "answer_sheet_id", nullable = false, unique = true)
    private AnswerSheet answerSheet;

    public SurveyResponse()
    {
    }

    public SurveyResponse( Survey survey )
    {
        assert survey.isPublished();

        this.survey = survey;
        this.answerSheet = new AnswerSheet( survey.getQuestionSheet() );
    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public Survey getSurvey()
    {
        return survey;
    }

    public void setSurvey( Survey survey )
    {
        this.survey = survey;
    }

    public AnswerSheet getAnswerSheet()
    {
        return answerSheet;
    }

    public void setAnswerSheet( AnswerSheet answerSheet )
    {
        this.answerSheet = answerSheet;
    }

}
