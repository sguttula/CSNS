package csns.importer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import csns.importer.parser.RosterParser;
import csns.model.academics.Section;

@Component
@Scope("prototype")
public class RosterImporter {

    @Autowired
    RosterParser rosterParser;

    Section section;

    String text;

    List<ImportedUser> importedStudents;

    public RosterImporter()
    {
        importedStudents = new ArrayList<ImportedUser>();
    }

    public void selectParser( int parser )
    {
        rosterParser.selectParser( parser );
    }

    public void clear()
    {
        importedStudents.clear();
        text = "";
    }

    public Section getSection()
    {
        return section;
    }

    public void setSection( Section section )
    {
        this.section = section;
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
            importedStudents = rosterParser.parse( text );
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
