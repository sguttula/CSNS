package csns.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import csns.model.academics.Department;
import csns.model.academics.Program;
import csns.model.academics.dao.DepartmentDao;
import csns.model.academics.dao.ProgramDao;
import csns.security.SecurityUtils;

@Controller
@SessionAttributes("program")
public class ProgramControllerS {

    @Autowired
    private ProgramDao programDao;

    @Autowired
    private DepartmentDao departmentDao;

    private static final Logger logger = LoggerFactory
        .getLogger( ProgramControllerS.class );

    @RequestMapping(value = "/department/{dept}/program/create",
        method = RequestMethod.GET)
    public String create( @PathVariable String dept, ModelMap models )
    {
        Department department = departmentDao.getDepartment( dept );
        models.put( "program", new Program( department ) );
        return "program/create";
    }

    @RequestMapping(value = "/department/{dept}/program/create",
        method = RequestMethod.POST)
    public String create( @ModelAttribute Program program,
        SessionStatus sessionStatus )
    {
        program = programDao.saveProgram( program );
        sessionStatus.setComplete();

        logger.info( SecurityUtils.getUser().getUsername() + "created program "
            + program.getId() );

        return "redirect:view?id=" + program.getId();
    }

    @RequestMapping(value = "/department/{dept}/program/edit",
        method = RequestMethod.GET)
    public String edit( @RequestParam Long id, ModelMap models )
    {
        models.put( "program", programDao.getProgram( id ) );
        return "program/edit";
    }

    @RequestMapping(value = "/department/{dept}/program/edit",
        method = RequestMethod.POST)
    public String edit( @ModelAttribute Program program,
        SessionStatus sessionStatus )
    {
        program = programDao.saveProgram( program );
        sessionStatus.setComplete();

        logger.info( SecurityUtils.getUser().getUsername() + "edited program "
            + program.getId() );

        return "redirect:view?id=" + program.getId();
    }

}
