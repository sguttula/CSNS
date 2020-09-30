package csns.model.qa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import csns.model.core.File;

@Entity
@DiscriminatorValue("TEXT")
public class TextAnswer extends Answer {

    private static final long serialVersionUID = 1L;

    private String text;

    @OneToOne
    @JoinColumn(name = "attachment_id")
    private File attachment;

    public TextAnswer()
    {
    }

    public TextAnswer( TextQuestion textQuestion )
    {
        super( textQuestion );
    }

    @Override
    public int check()
    {
        return 0;
    }

    @Override
    public String toString()
    {
        return text != null ? text : "";
    }

    public String getText()
    {
        return text;
    }

    public void setText( String text )
    {
        this.text = text;
    }

    public File getAttachment()
    {
        return attachment;
    }

    public void setAttachment( File attachment )
    {
        this.attachment = attachment;
    }

}
