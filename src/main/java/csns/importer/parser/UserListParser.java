package csns.importer.parser;

import java.util.List;

import csns.importer.ImportedUser;

public interface UserListParser {

    List<ImportedUser> parse( String text );

}
