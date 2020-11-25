package csns.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import csns.model.academics.Assignment;
import csns.model.academics.Section;
import csns.model.academics.dao.AssignmentDao;
import csns.model.academics.dao.SectionDao;
import csns.model.core.Resource;
import csns.model.core.dao.ResourceDao;
import csns.security.SecurityUtils;
import csns.util.FileIO;

@Controller
public class ResourceController {

    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    private SectionDao sectionDao;

    @Autowired
    private AssignmentDao assignmentDao;

    @Autowired
    private FileIO fileIO;

    private static final Logger logger = LoggerFactory
        .getLogger( ResourceController.class );

    private String view( Resource resource, ModelMap models,
        HttpServletResponse response )
    {
        if( resource.isDeleted() )
        {
            models.put( "message", "error.resource.deleted" );
            return "error";
        }

        switch( resource.getType() )
        {
            case TEXT:
                models.put( "resource", resource );
                return "resource/view";

            case FILE:
                fileIO.write( resource.getFile(), response );
                return null;

            case URL:
                return "redirect:" + resource.getUrl();

            default:
                logger.warn( "Invalid resource type: " + resource.getType() );
                models.put( "message", "error.resource.type.invalid" );
                return "error";
        }
    }

    @RequestMapping("/resource/view")
    public String view( @RequestParam Long id, ModelMap models,
        HttpServletResponse response )
    {
        return view( resourceDao.getResource( id ), models, response );
    }

    @RequestMapping("/department/{dept}/journal/viewAssignment")
    public String viewAssignment( @RequestParam Long assignmentId,
        ModelMap models, HttpServletResponse response )
    {
        Assignment assignment = assignmentDao.getAssignment( assignmentId );
        models.put( "view", "CourseJournal" );
        models.put( "assignment", assignment );
        return view( assignment.getDescription(), models, response );
    }

    @RequestMapping("/section/journal/removeHandout")
    public String remove( @RequestParam Long sectionId,
        @RequestParam Long resourceId )
    {
        String username = SecurityUtils.getUser().getUsername();
        Section section = sectionDao.getSection( sectionId );
        boolean removed = section.getJournal().removeHandout( resourceId );
        if( removed )
        {
            section = sectionDao.saveSection( section );
            logger.info( username + " removed handout " + resourceId
                + " from section " + sectionId );
        }
        else
            logger.info( username + " failed to remove handout " + resourceId
                + " from section " + sectionId );

        return "redirect:/section/journal/handouts?sectionId="
            + section.getId();
    }

}
