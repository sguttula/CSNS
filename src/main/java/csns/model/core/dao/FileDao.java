package csns.model.core.dao;
 
import java.util.List;

import csns.model.core.File;
import csns.model.core.User;

public interface FileDao {

    File getFile( Long id );

    List<File> getFiles( User owner, File parent, String name, boolean isFolder );

    File getCKEditorFolder( User owner );

    List<File> listFiles( User owner );

    List<File> listFiles( File parent );

    List<File> listFolders( User owner );

    List<File> listFolders( File parent );

    File saveFile( File file );

    long getDiskUsage( User user );

}
