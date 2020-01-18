package csns.importer.parser.csula;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import csns.importer.ImportedUser;
import csns.importer.parser.UserListParser;
import csns.model.academics.Term;

@Component("studentsParser")
public class StudentsParserImpl implements UserListParser {

    @Override
    public List<ImportedUser> parse( String text )
    {
        List<ImportedUser> students = new ArrayList<ImportedUser>();

        Scanner scanner = new Scanner( text );
        while( scanner.hasNextLine() )
        {
            ImportedUser student = parseLine( scanner.nextLine() );
            if( student != null ) students.add( student );
        }
        scanner.close();

        return students;
    }

    public ImportedUser parseLine( String line )
    {
        ImportedUser student = null;

        String tokens[] = line.trim().split( "\t" );
        if( tokens.length >= 4 && isTerm( tokens[0] ) && isCin( tokens[1] ) )
        {
            student = new ImportedUser();
            // GET term code is different from CSNS term code
            student.setTerm( new Term( Integer.parseInt( tokens[0] ) - 1000 ) );
            student.setCin( tokens[1] );
            student.setFirstName( tokens[2] );
            student.setLastName( tokens[3] );
        }

        return student;
    }

    public boolean isTerm( String s )
    {
        return s.matches( "\\d{4}" );
    }

    public boolean isCin( String s )
    {
        return s.matches( "\\d{9}" );
    }

}
