package csns.web.controller;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import csns.model.academics.Department;
import csns.model.academics.Program;
import csns.model.academics.dao.DepartmentDao;
import csns.model.academics.dao.ProgramDao;
import csns.model.core.User;
import csns.security.SecurityUtils;

@Controller
public class ProgramController {

    @Autowired
    private ProgramDao programDao;

    @Autowired
    private DepartmentDao departmentDao;

    private static final Logger logger = LoggerFactory
        .getLogger( ProgramController.class );

    @RequestMapping("/program/search")
    public String search( @RequestParam(required = false) String text,
        ModelMap models )
    {
        if( StringUtils.hasText( text ) ) models.addAttribute( "programs",
            programDao.searchPrograms( text, -1 ) );
        return "program/search";
    }

    @RequestMapping("/department/{dept}/program/list")
    public String list( @PathVariable String dept, ModelMap models )
    {
        Department department = departmentDao.getDepartment( dept );
        models.put( "department", department );
        models.put( "programs", programDao.getPrograms( department ) );
        return "program/list";
    }

    @RequestMapping("/department/{dept}/program/view")
    public String view( @RequestParam Long id, ModelMap models )
    {
        models.put( "program", programDao.getProgram( id ) );
        return "program/view";
    }

    @RequestMapping("/department/{dept}/program/publish")
    public String publish( @RequestParam Long id )
    {
        Program program = programDao.getProgram( id );
        if( !program.isPublished() )
        {
            User user = SecurityUtils.getUser();
            program.setPublishDate( Calendar.getInstance() );
            program.setPublishedBy( user );
            program = programDao.saveProgram( program );

            logger.info(
                user.getUsername() + " published program " + program.getId() );
        }

        return "redirect:list";
    }

    @RequestMapping("/department/{dept}/program/clone")
    public String clone( @RequestParam Long id, ModelMap models )
    {
        Program program = programDao.getProgram( id );
        Program newProgram = programDao.saveProgram( program.clone() );

        logger.info( SecurityUtils.getUser().getUsername() + " created program "
            + newProgram.getId() + " from " + program.getId() );

        return "redirect:view?id=" + newProgram.getId();
    }

    @RequestMapping("/department/{dept}/program/remove")
    public String remove( @RequestParam Long id )
    {
        Program program = programDao.getProgram( id );
        program.setObsolete( true );
        program = programDao.saveProgram( program );

        logger.info( SecurityUtils.getUser().getUsername() + " removed program "
            + program.getId() );

        return "redirect:list";
    }

}
