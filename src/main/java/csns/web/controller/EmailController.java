package csns.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import csns.helper.Email;
import csns.model.core.User;
import csns.model.core.dao.UserDao;
import csns.security.SecurityUtils;
import csns.util.EmailUtils;
import csns.util.FileIO;
import csns.web.validator.EmailValidator;

@Controller
@SessionAttributes("email")
public class EmailController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private FileIO fileIO;

    @Autowired
    private EmailUtils emailUtils;

    @Autowired
    private EmailValidator emailValidator;

    @RequestMapping("/email/compose")
    public String compose(
        @RequestParam(value = "userId", required = false) Long ids[],
        @RequestParam(required = false) Boolean useSecondaryEmail,
        ModelMap models )
    {
        Email email = new Email();
        email.setAuthor( SecurityUtils.getUser() );
        email.setRecipients( userDao.getUsers( ids ) );
        if( useSecondaryEmail != null )
            email.setUseSecondaryEmail( useSecondaryEmail );

        models.put( "email", email );
        return "email/compose";
    }

    @RequestMapping(value = "/email/compose", params = "send")
    public String compose( @ModelAttribute Email email,
        @RequestParam("userId") Long ids[], @RequestParam(value = "file",
            required = false) MultipartFile[] uploadedFiles,
        @RequestParam(value = "backUrl", required = false) String backUrl,
        BindingResult result, SessionStatus sessionStatus, ModelMap models )
    {
        email.setRecipients( userDao.getUsers( ids ) );
        emailValidator.validate( email, result );
        if( result.hasErrors() ) return "email/compose";

        User user = SecurityUtils.getUser();
        if( uploadedFiles != null )
            email.getAttachments().addAll(
                fileIO.save( uploadedFiles, user, true ) );

        emailUtils.sendTextMail( email );
        sessionStatus.setComplete();

        models.put( "backUrl", backUrl );
        models.put( "message", "status.email.sent" );
        return "status";
    }

}
