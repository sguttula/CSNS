package csns.web.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import csns.model.academics.Assignment;
import csns.model.academics.Enrollment;
import csns.model.academics.Section;
import csns.model.academics.dao.AssignmentDao;
import csns.model.academics.dao.EnrollmentDao;
import csns.model.academics.dao.SectionDao;
import csns.model.assessment.CourseJournal;
import csns.model.assessment.RubricAssignment;
import csns.model.assessment.dao.CourseJournalDao;
import csns.model.assessment.dao.RubricAssignmentDao;
import csns.model.core.User;
import csns.model.site.Block;
import csns.model.site.Item;
import csns.model.site.Site;
import csns.security.SecurityUtils;

@Controller
public class SectionJournalController {

    @Autowired
    private SectionDao sectionDao;

    @Autowired
    private CourseJournalDao courseJournalDao;

    @Autowired
    private AssignmentDao assignmentDao;

    @Autowired
    private RubricAssignmentDao rubricAssignmentDao;

    @Autowired
    private EnrollmentDao enrollmentDao;

    private static final Logger logger = LoggerFactory
        .getLogger( SectionJournalController.class );

    @RequestMapping("/section/journal/create")
    public String create( @RequestParam Long sectionId )
    {
        Section section = sectionDao.getSection( sectionId );
        if( section.getJournal() != null )
            return "redirect:view?sectionId=" + sectionId;

        CourseJournal journal = new CourseJournal( section );
        section.setJournal( journal );

        // Populate handouts if the section has a class website
        Site site = section.getSite();
        if( site != null ) for( Block block : site.getBlocks() )
            if( block.getType().equals( Block.Type.REGULAR ) )
                for( Item item : block.getItems() )
                journal.getHandouts().add( item.getResource().clone() );

        // Populate assignments
        journal.getAssignments().addAll( section.getAssignments() );
        journal.getRubricAssignments().addAll( section.getRubricAssignments() );

        section = sectionDao.saveSection( section );
        logger.info( SecurityUtils.getUser().getUsername()
            + " created course journal for section " + sectionId );

        return "redirect:view?sectionId=" + sectionId;
    }

    @RequestMapping("/section/journal/recreate")
    public String recreate( @RequestParam Long sectionId, ModelMap models )
    {
        Section section = sectionDao.getSection( sectionId );
        CourseJournal journal = section.getJournal();
        if( journal == null || journal.isSubmitted() || journal.isApproved() )
        {
            models.put( "message", "error.section.journal.recreate" );
            models.put( "backUrl", "/section/taught" );
            return "error";
        }

        journal.getHandouts().clear();
        Site site = section.getSite();
        if( site != null ) for( Block block : site.getBlocks() )
            if( block.getType().equals( Block.Type.REGULAR ) )
                for( Item item : block.getItems() )
                journal.getHandouts().add( item.getResource().clone() );

        journal.getAssignments().clear();
        journal.getAssignments().addAll( section.getAssignments() );
        journal.getRubricAssignments().clear();
        journal.getRubricAssignments().addAll( section.getRubricAssignments() );

        section = sectionDao.saveSection( section );
        logger.info( SecurityUtils.getUser().getUsername()
            + " recreated course journal for section " + sectionId );

        return "redirect:view?sectionId=" + sectionId;
    }

    @RequestMapping("/section/journal/view")
    public String view( @RequestParam Long sectionId, ModelMap models )
    {
        Section section = sectionDao.getSection( sectionId );
        if( section.getJournal() == null )
        {
            models.put( "message", "error.section.nosyllabus" );
            return "error";
        }

        models.put( "section", section );
        User coordinator = section.getCourse().getCoordinator();
        if( coordinator != null ) models.put( "isCoordinator",
            coordinator.getId().equals( SecurityUtils.getUser().getId() ) );

        return "section/journal/view";
    }

    @RequestMapping("/section/journal/submit")
    public String submit( @RequestParam Long sectionId )
    {
        Section section = sectionDao.getSection( sectionId );
        if( section.getJournal().getSubmitDate() == null )
        {
            section.getJournal().setSubmitDate( new Date() );
            section = sectionDao.saveSection( section );
            logger.info( SecurityUtils.getUser().getUsername()
                + " submitted course journal for section " + sectionId );
        }

        return "redirect:view?sectionId=" + sectionId;
    }

    @RequestMapping("/section/journal/handouts")
    public String handouts( @RequestParam Long sectionId, ModelMap models )
    {
        models.put( "section", sectionDao.getSection( sectionId ) );
        return "section/journal/handouts";
    }

    @RequestMapping("/section/journal/assignments")
    public String assignments( @RequestParam Long sectionId, ModelMap models )
    {
        models.put( "section", sectionDao.getSection( sectionId ) );
        return "section/journal/assignments";
    }

    @RequestMapping("/section/journal/toggleAssignment")
    @ResponseStatus(HttpStatus.OK)
    public void toggleAssignment( @RequestParam Long journalId,
        @RequestParam Long assignmentId )
    {
        User user = SecurityUtils.getUser();
        CourseJournal courseJournal = courseJournalDao
            .getCourseJournal( journalId );
        Assignment assignment = assignmentDao.getAssignment( assignmentId );

        List<Assignment> assignments = courseJournal.getAssignments();
        if( assignments.contains( assignment ) )
        {
            assignments.remove( assignment );
            logger.info( user.getUsername() + " removed assignment "
                + assignmentId + " from course jorunal " + journalId );
        }
        else
        {
            assignments.add( assignment );
            logger.info( user.getUsername() + " added assignment "
                + assignmentId + " to course jorunal " + journalId );
        }
        courseJournalDao.saveCourseJournal( courseJournal );
    }

    @RequestMapping("/section/journal/toggleRubricAssignment")
    @ResponseStatus(HttpStatus.OK)
    public void toggleRubricAssignment( @RequestParam Long journalId,
        @RequestParam Long assignmentId )
    {
        User user = SecurityUtils.getUser();
        CourseJournal courseJournal = courseJournalDao
            .getCourseJournal( journalId );
        RubricAssignment assignment = rubricAssignmentDao
            .getRubricAssignment( assignmentId );

        List<RubricAssignment> assignments = courseJournal
            .getRubricAssignments();
        if( assignments.contains( assignment ) )
        {
            assignments.remove( assignment );
            logger.info( user.getUsername() + " removed rubric assignment "
                + assignmentId + " from course jorunal " + journalId );
        }
        else
        {
            assignments.add( assignment );
            logger.info( user.getUsername() + " added rubric assignment "
                + assignmentId + " to course jorunal " + journalId );
        }
        courseJournalDao.saveCourseJournal( courseJournal );
    }

    @RequestMapping("/section/journal/samples")
    public String samples( @RequestParam Long sectionId, ModelMap models )
    {
        models.put( "section", sectionDao.getSection( sectionId ) );
        return "section/journal/samples";
    }

    @RequestMapping("/section/journal/toggleEnrollment")
    @ResponseStatus(HttpStatus.OK)
    public void toggleEnrollment( @RequestParam Long journalId,
        @RequestParam Long enrollmentId )
    {
        User user = SecurityUtils.getUser();
        CourseJournal courseJournal = courseJournalDao
            .getCourseJournal( journalId );
        Enrollment enrollment = enrollmentDao.getEnrollment( enrollmentId );

        List<Enrollment> samples = courseJournal.getStudentSamples();
        if( samples.contains( enrollment ) )
        {
            samples.remove( enrollment );
            logger.info( user.getUsername() + " removed enrollment "
                + enrollmentId + " from course jorunal " + journalId );
        }
        else
        {
            samples.add( enrollment );
            logger.info( user.getUsername() + " added enrollment "
                + enrollmentId + " to course jorunal " + journalId );
        }
        courseJournalDao.saveCourseJournal( courseJournal );
    }

}
