package csns.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import csns.model.academics.Program;
import csns.model.academics.ProgramBlock;
import csns.model.academics.dao.ProgramBlockDao;
import csns.model.academics.dao.ProgramDao;
import csns.security.SecurityUtils;

@Controller
@SessionAttributes("block")
public class ProgramBlockControllerS {

    @Autowired
    private ProgramDao programDao;

    @Autowired
    private ProgramBlockDao programBlockDao;

    private static final Logger logger = LoggerFactory
        .getLogger( ProgramBlockControllerS.class );

    @RequestMapping(value = "/department/{dept}/program/block/add",
        method = RequestMethod.GET)
    public String add( @RequestParam Long programId, ModelMap models )
    {
        models.put( "program", programDao.getProgram( programId ) );
        models.put( "block", new ProgramBlock() );
        return "program/block/add";
    }

    @RequestMapping(value = "/department/{dept}/program/block/add",
        method = RequestMethod.POST)
    public String add( @RequestParam Long programId,
        @ModelAttribute("block") ProgramBlock block,
        SessionStatus sessionStatus )
    {
        Program program = programDao.getProgram( programId );
        program.getBlocks().add( block );
        program = programDao.saveProgram( program );
        sessionStatus.setComplete();

        logger.info( SecurityUtils.getUser().getUsername() + " added block ["
            + block.getName() + "] to program " + programId );

        return "redirect:list?programId=" + programId;
    }

    @RequestMapping(value = "/department/{dept}/program/block/edit",
        method = RequestMethod.GET)
    public String edit( @RequestParam Long id, @RequestParam Long programId,
        ModelMap models )
    {
        models.put( "block", programBlockDao.getProgramBlock( id ) );
        models.put( "program", programDao.getProgram( programId ) );
        return "program/block/edit";
    }

    @RequestMapping(value = "/department/{dept}/program/block/edit",
        method = RequestMethod.POST)
    public String edit( @RequestParam Long programId,
        @ModelAttribute("block") ProgramBlock block,
        SessionStatus sessionStatus )
    {
        if( block.isRequireAll() ) block.setUnitsRequired( null );
        block = programBlockDao.saveProgramBlock( block );
        sessionStatus.setComplete();

        logger.info( SecurityUtils.getUser().getUsername() + " edited block "
            + block.getId() );

        return "redirect:list?programId=" + programId;
    }

}
