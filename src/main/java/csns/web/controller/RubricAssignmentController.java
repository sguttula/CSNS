package csns.web.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import csns.model.assessment.RubricAssignment;
import csns.model.assessment.dao.RubricAssignmentDao;
import csns.security.SecurityUtils;

@Controller
public class RubricAssignmentController {

    @Autowired
    private RubricAssignmentDao rubricAssignmentDao;

    private static final Logger logger = LoggerFactory.getLogger( RubricAssignmentController.class );

    @RequestMapping("/rubric/assignment/delete")
    public String delete( @RequestParam Long id )
    {
        RubricAssignment assignment = rubricAssignmentDao.getRubricAssignment( id );
        assignment.setDeleted( true );
        assignment = rubricAssignmentDao.saveRubricAssignment( assignment );

        logger.info( SecurityUtils.getUser().getUsername()
            + " deleted rubric assignment " + assignment.getId() );

        return "redirect:/section/taught#section-"
            + assignment.getSection().getId();
    }

    @RequestMapping("/rubric/assignment/publish")
    @ResponseBody
    public String publish( @RequestParam Long id ) throws IOException
    {
        RubricAssignment assignment = rubricAssignmentDao.getRubricAssignment( id );
        if( !assignment.isPublished() )
        {
            assignment.setPublishDate( Calendar.getInstance() );
            assignment = rubricAssignmentDao.saveRubricAssignment( assignment );

            logger.info( SecurityUtils.getUser().getUsername()
                + " published rubric assignment " + assignment.getId() );
        }

        DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd hh:mm a" );
        return dateFormat.format( assignment.getPublishDate().getTime() );
    }

}
