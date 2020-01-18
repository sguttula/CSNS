package csns.importer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import csns.importer.parser.UserListParser;
import csns.model.academics.Department;
import csns.model.academics.Standing;

@Component
@Scope("prototype")
public class StudentsImporter {

    @Autowired
    @Qualifier("studentsParser")
    UserListParser usersParser;

    Department department;

    Standing standing;

    String text;

    List<ImportedUser> importedStudents;

    public StudentsImporter()
    {
        importedStudents = new ArrayList<ImportedUser>();
    }

    public Department getDepartment()
    {
        return department;
    }

    public void setDepartment( Department department )
    {
        this.department = department;
    }

    public Standing getStanding()
    {
        return standing;
    }

    public void setStanding( Standing standing )
    {
        this.standing = standing;
    }

    public String getText()
    {
        return text;
    }

    public void setText( String text )
    {
        if( StringUtils.hasText( text ) )
        {
            this.text = text;
            importedStudents = usersParser.parse( text );
        }
    }

    public List<ImportedUser> getImportedStudents()
    {
        return importedStudents;
    }

    public void setImportedStudents( List<ImportedUser> importedStudents )
    {
        this.importedStudents = importedStudents;
    }

}
