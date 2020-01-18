package csns.importer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import csns.model.academics.Department;
import csns.model.assessment.MFTScore;
import csns.model.core.User;

public class MFTScoreImporter {

    private Department department;

    private Date date;

    private String text;

    private List<MFTScore> scores;

    private List<User> failedUsers;

    public MFTScoreImporter()
    {
        scores = new ArrayList<MFTScore>();
        failedUsers = new ArrayList<User>();
    }

    public void clear()
    {
        scores.clear();
        failedUsers.clear();
    }

    public Department getDepartment()
    {
        return department;
    }

    public void setDepartment( Department department )
    {
        this.department = department;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate( Date date )
    {
        this.date = date;
    }

    public String getText()
    {
        return text;
    }

    public void setText( String text )
    {
        this.text = text;
    }

    public List<MFTScore> getScores()
    {
        return scores;
    }

    public void setScores( List<MFTScore> scores )
    {
        this.scores = scores;
    }

    public List<User> getFailedUsers()
    {
        return failedUsers;
    }

    public void setFailedUsers( List<User> failedUsers )
    {
        this.failedUsers = failedUsers;
    }

}
