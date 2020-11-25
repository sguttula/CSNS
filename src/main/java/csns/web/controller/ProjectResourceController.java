package csns.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import csns.model.academics.Project;
import csns.model.academics.dao.ProjectDao;
import csns.model.core.Resource;
import csns.model.core.User;
import csns.security.SecurityUtils;
import csns.util.FileIO;

@Controller
public class ProjectResourceController {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private FileIO fileIO;

    private static final Logger logger = LoggerFactory
        .getLogger( ProjectResourceController.class );

    @RequestMapping("/department/{dept}/project/resource/view")
    public String view( @PathVariable String dept, @RequestParam Long projectId,
        @RequestParam Long resourceId, ModelMap models,
        HttpServletResponse response )
    {
        User user = SecurityUtils.getUser();
        Project project = projectDao.getProject( projectId );
        Resource resource = project.getResource( resourceId );

        if( resource.isPrivate() && !project.isMember( user ) )
        {
            String backUrl = "/department/" + dept + "/project/view?id="
                + projectId;
            models.put( "backUrl", backUrl );
            models.put( "message", "error.project.resource.private" );
            return "error";
        }

        switch( resource.getType() )
        {
            case TEXT:
                models.put( "project", project );
                models.put( "resource", resource );
                models.put( "user", SecurityUtils.getUser() );
                return "project/resource/view";

            case FILE:
                fileIO.write( resource.getFile(), response );
                return null;

            case URL:
                return "redirect:" + resource.getUrl();

            default:
                logger.warn( "Invalid resource type: " + resource.getType() );
                return "redirect:/department/" + dept + "/project/view?id="
                    + projectId;
        }
    }

    @RequestMapping("/department/{dept}/project/resource/reorder")
    @ResponseBody
    public void reorder( @RequestParam Long projectId,
        @RequestParam Long resourceId, @RequestParam int newIndex )
    {
        Project project = projectDao.getProject( projectId );
        Resource resource = project.removeResource( resourceId );
        if( resource != null )
        {
            project.getResources().add( newIndex, resource );
            projectDao.saveProject( project );

            logger.info( SecurityUtils.getUser().getUsername()
                + " reordered resource " + resourceId + " of project "
                + projectId + " to index " + newIndex );
        }
    }

    @RequestMapping("/department/{dept}/project/resource/delete")
    public String delete( @PathVariable String dept,
        @RequestParam Long projectId, @RequestParam Long resourceId )
    {
        Project project = projectDao.getProject( projectId );
        project.removeResource( resourceId );
        projectDao.saveProject( project );

        logger.info( SecurityUtils.getUser().getUsername()
            + " deleted resource " + resourceId + " of project " + projectId );

        return "redirect:/department/" + dept + "/project/view?id=" + projectId;
    }

}
