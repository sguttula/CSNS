package csns.model.qa;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("TEXT")
public class TextQuestion extends Question {

    private static final long serialVersionUID = 1L;

    @Column(name = "correct_answer")
    protected String correctAnswer;

    @Column(name = "text_length")
    protected int textLength;

    @Column(name = "attachment_allowed", nullable = false)
    protected boolean attachmentAllowed;

    public TextQuestion()
    {
        textLength = 20;
        attachmentAllowed = false;
    }

    @Override
    public String getType()
    {
        return "TEXT";
    }

    @Override
    public Answer createAnswer()
    {
        TextAnswer answer = new TextAnswer( this );
        answers.add( answer );
        return answer;
    }

    @Override
    public Question clone()
    {
        TextQuestion newQuestion = new TextQuestion();

        newQuestion.description = description;
        newQuestion.pointValue = pointValue;
        newQuestion.textLength = textLength;
        newQuestion.correctAnswer = correctAnswer;
        newQuestion.attachmentAllowed = attachmentAllowed;

        return newQuestion;
    }

    public String getCorrectAnswer()
    {
        return correctAnswer;
    }

    public void setCorrectAnswer( String correctAnswer )
    {
        this.correctAnswer = correctAnswer;
    }

    public int getTextLength()
    {
        return textLength;
    }

    public void setTextLength( int textLength )
    {
        this.textLength = textLength >= 1 ? textLength : 1;
    }

    public boolean isAttachmentAllowed()
    {
        return attachmentAllowed;
    }

    public void setAttachmentAllowed( boolean attachmentAllowed )
    {
        this.attachmentAllowed = attachmentAllowed;
    }

}
