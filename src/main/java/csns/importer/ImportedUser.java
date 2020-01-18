package csns.importer;

import java.io.Serializable;

import csns.model.academics.Term;

public class ImportedUser implements Serializable {

    private static final long serialVersionUID = 1L;

    String cin;

    String firstName, lastName, middleName;

    String grade, oldGrade;

    Term term;

    boolean isNewAccount;

    boolean isNewEnrollment;

    boolean isNewMember;

    public ImportedUser()
    {
        isNewAccount = false;
        isNewEnrollment = false;
        isNewMember = false;
    }

    public void setName( String name )
    {
        name = name.trim();
        int index1 = name.indexOf( ',' );
        int index2 = name.indexOf( ' ', index1 );

        lastName = name.substring( 0, index1 );
        if( index2 > 0 )
        {
            firstName = name.substring( index1 + 1, index2 );
            middleName = name.substring( index2 + 1 );
        }
        else
        {
            firstName = name.substring( index1 + 1 );
            middleName = "";
        }
    }

    public String getCin()
    {
        return cin;
    }

    public void setCin( String cin )
    {
        this.cin = cin;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName( String middleName )
    {
        this.middleName = middleName;
    }

    public String getGrade()
    {
        return grade;
    }

    public void setGrade( String grade )
    {
        this.grade = grade;
    }

    public String getOldGrade()
    {
        return oldGrade;
    }

    public void setOldGrade( String oldGrade )
    {
        this.oldGrade = oldGrade;
    }

    public Term getTerm()
    {
        return term;
    }

    public void setTerm( Term term )
    {
        this.term = term;
    }

    public boolean isNewAccount()
    {
        return isNewAccount;
    }

    public void setNewAccount( boolean isNewAccount )
    {
        this.isNewAccount = isNewAccount;
    }

    public boolean isNewEnrollment()
    {
        return isNewEnrollment;
    }

    public void setNewEnrollment( boolean isNewEnrollment )
    {
        this.isNewEnrollment = isNewEnrollment;
    }

    public boolean isNewMember()
    {
        return isNewMember;
    }

    public void setNewMember( boolean isNewMember )
    {
        this.isNewMember = isNewMember;
    }

}
