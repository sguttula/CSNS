package csns.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import csns.model.advisement.AdvisementRecord;
import csns.model.advisement.dao.AdvisementRecordDao;
import csns.model.core.User;
import csns.model.core.dao.UserDao;
import csns.security.SecurityUtils;
import csns.util.FileIO;

@Controller
public class UserAdvisementController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AdvisementRecordDao advisementRecordDao;

    @Autowired
    private FileIO fileIO;

    private static final Logger logger = LoggerFactory.getLogger( UserAdvisementController.class );

    @RequestMapping("/user/advisement")
    public String advisement( @RequestParam Long userId, ModelMap models )
    {
        User user = userDao.getUser( userId );
        models.put( "user", user );
        models.put( "self", SecurityUtils.getUser() );
        models.put( "advisementRecords",
            advisementRecordDao.getAdvisementRecords( user ) );
        return "user/advisement";
    }

    @RequestMapping("/user/advisement/add")
    public String add(
        @RequestParam Long userId,
        @RequestParam String comment,
        @RequestParam(required = false) Boolean forAdvisorsOnly,
        @RequestParam(value = "file", required = false) MultipartFile[] uploadedFiles )
    {
        User student = userDao.getUser( userId );
        AdvisementRecord record = new AdvisementRecord( student,
            SecurityUtils.getUser() );
        record.setComment( comment );
        if( forAdvisorsOnly != null )
            record.setForAdvisorsOnly( forAdvisorsOnly );
        if( uploadedFiles != null )
            record.getAttachments().addAll(
                fileIO.save( uploadedFiles, student, false ) );

        record = advisementRecordDao.saveAdvisementRecord( record );

        logger.info( SecurityUtils.getUser().getUsername()
            + " created advisement record " + record.getId() );

        // Advisement is the 5th tab
        return "redirect:/user/view?id=" + userId + "#4";
    }

}
