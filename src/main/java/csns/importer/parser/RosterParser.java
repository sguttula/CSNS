package csns.importer.parser;

import java.util.List;

import csns.importer.ImportedUser;

public interface RosterParser {

    void selectParser( int parser );

    List<ImportedUser> parse( String text );

}
