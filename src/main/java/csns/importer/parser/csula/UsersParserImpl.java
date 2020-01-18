package csns.importer.parser.csula;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import csns.importer.ImportedUser;
import csns.importer.parser.UserListParser;

@Component("usersParser")
public class UsersParserImpl implements UserListParser {

    @Override
    public List<ImportedUser> parse( String text )
    {
        List<ImportedUser> importedUsers = new ArrayList<ImportedUser>();

        Scanner scanner = new Scanner( text );
        while( scanner.hasNextLine() )
        {
            ImportedUser user = parseLine( scanner.nextLine() );
            if( user != null ) importedUsers.add( user );
        }
        scanner.close();

        return importedUsers;
    }

    public ImportedUser parseLine( String line )
    {
        ImportedUser user = null;

        String tokens[] = line.trim().split( "\t" );
        if( tokens.length >= 3 && isCin( tokens[0] ) )
        {
            user = new ImportedUser();
            user.setCin( tokens[0] );
            user.setFirstName( tokens[1] );
            user.setLastName( tokens[2] );
        }

        return user;
    }

    public boolean isCin( String s )
    {
        return s.matches( "\\d{9}" );
    }

}
