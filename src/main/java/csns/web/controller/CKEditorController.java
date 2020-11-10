package csns.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import csns.model.core.File;
import csns.model.core.User;
import csns.model.core.dao.FileDao;
import csns.security.SecurityUtils;
import csns.util.FileIO;

@Controller
public class CKEditorController {

    @Autowired
    private FileDao fileDao;

    @Autowired
    private FileIO fileIO;

    private static final Logger logger = LoggerFactory.getLogger( CKEditorController.class );

    @RequestMapping("/ckeditor/upload")
    public String upload( @RequestParam("upload") MultipartFile uploadedFile,
        ModelMap models )
    {
        User user = SecurityUtils.getUser();
        File parent = fileDao.getCKEditorFolder( user );
        File file = fileIO.save( uploadedFile, user, parent, true );

        logger.info( user.getUsername() + " uploaded (via CKEditor) file "
            + file.getId() );

        models.put( "file", file );
        return "ckeditor/upload";
    }

}
