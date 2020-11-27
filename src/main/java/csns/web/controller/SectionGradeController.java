package csns.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import csns.model.academics.Course;
import csns.model.academics.Enrollment;
import csns.model.academics.Grade;
import csns.model.academics.Section;
import csns.model.academics.dao.EnrollmentDao;
import csns.model.academics.dao.GradeDao;
import csns.model.academics.dao.SectionDao;
import csns.model.academics.dao.SubmissionDao;
import csns.model.assessment.dao.RubricSubmissionDao;
import csns.model.core.User;
import csns.security.SecurityUtils;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Controller
public class SectionGradeController {

    @Autowired
    private GradeDao gradeDao;

    @Autowired
    private SectionDao sectionDao;

    @Autowired
    private EnrollmentDao enrollmentDao;

    @Autowired
    private SubmissionDao submissionDao;

    @Autowired
    private RubricSubmissionDao rubricSubmissionDao;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private Configuration freemarkerConfiguration;

    private static final Logger logger = LoggerFactory
        .getLogger( SectionGradeController.class );

    @RequestMapping(value = "/section/grade",
        params = { "!grade", "!gradeId", "!comments" })
    public String grade( @RequestParam Long enrollmentId, ModelMap models )
        throws JSONException
    {
        List<Grade> grades = gradeDao.getGrades();

        // The JSON "array" used by jEditable to generate the select list
        // is not really an array - it's a quoted JSON object. Because
        // object properties are not ordered, we can't use JSONObject, and
        // because it's not an array, we can't use JSONArray either, which
        // leave us to assemble the damn thing manually.
        StringBuffer sb = new StringBuffer( "{'0':'-'," );
        for( int i = 0; i < grades.size() - 1; ++i )
            sb.append( "'" )
                .append( grades.get( i ).getId() )
                .append( "':'" )
                .append( grades.get( i ).getSymbol() )
                .append( "'," );
        sb.append( "'" )
            .append( grades.get( grades.size() - 1 ).getId() )
            .append( "':'" )
            .append( grades.get( grades.size() - 1 ).getSymbol() )
            .append( "'}" );
        models.put( "grades", sb.toString() );

        Enrollment enrollment = enrollmentDao.getEnrollment( enrollmentId );
        models.put( "enrollment", enrollment );
        models.put( "submissions", submissionDao.getSubmissions(
            enrollment.getStudent(), enrollment.getSection() ) );
        models.put( "rubricSubmissions",
            rubricSubmissionDao.getRubricSubmissions( enrollment.getStudent(),
                enrollment.getSection() ) );

        return "section/grade";
    }

    @RequestMapping(value = "/section/grade", params = "gradeId")
    public String editGrade( @RequestParam Long enrollmentId,
        @RequestParam Long gradeId, HttpServletResponse response )
        throws IOException, JSONException
    {
        Enrollment enrollment = enrollmentDao.getEnrollment( enrollmentId );
        enrollment.setGrade( gradeDao.getGrade( gradeId ) );
        enrollment.setGradeMailed( false );
        enrollmentDao.saveEnrollment( enrollment );

        logger.info( SecurityUtils.getUser().getUsername() + " set enrollment "
            + enrollment.getId() + " grade to "
            + enrollment.getGrade().getSymbol() );

        response.setContentType( "text/plain" );
        String grade = enrollment.getGrade() == null ? ""
            : enrollment.getGrade().getSymbol();
        response.getWriter().print( grade );
        return null;
    }

    @RequestMapping(value = "/section/grade", params = "comments")
    public String editComments( @RequestParam Long enrollmentId,
        @RequestParam String comments, HttpServletResponse response )
        throws IOException
    {
        Enrollment enrollment = enrollmentDao.getEnrollment( enrollmentId );
        enrollment.setComments( comments );
        enrollment = enrollmentDao.saveEnrollment( enrollment );

        logger.info( SecurityUtils.getUser().getUsername()
            + " changed enrollment " + enrollment.getId() + " comment " );

        response.setContentType( "text/plain" );
        response.getWriter().print( enrollment.getComments() );
        return null;
    }

    private void emailGrade( Enrollment enrollment )
        throws IOException, TemplateException
    {
        if( enrollment.getGrade() == null || enrollment.isGradeMailed() )
            return;

        User instructor = SecurityUtils.getUser();
        User student = enrollment.getStudent();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom( instructor.getEmail() );
        message.setTo( student.getEmail() );

        Course course = enrollment.getSection().getCourse();
        String subject = course.getCode() + " Grade";
        message.setSubject( subject );

        Map<String, Object> fModels = new HashMap<String, Object>();
        fModels.put( "grade", enrollment.getGrade().getSymbol() );
        String comments = enrollment.getComments();
        fModels.put( "comments", comments != null ? comments : "" );
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(
            freemarkerConfiguration.getTemplate( "email.section.grade.txt" ),
            fModels );
        message.setText( text );

        try
        {
            mailSender.send( message );
            enrollment.setGradeMailed( true );
            enrollmentDao.saveEnrollment( enrollment );
            logger.info( instructor.getUsername() + " sent " + course.getCode()
                + " grade to " + student.getEmail() );
        }
        catch( MailException e )
        {
            logger.warn( instructor.getUsername() + " failed to send "
                + course.getCode() + " grade to " + student.getEmail() );
            logger.debug( e.getMessage() );
        }
    }

    @RequestMapping(value = "/section/email", params = "enrollmentId")
    public String emailGrade( @RequestParam Long enrollmentId )
        throws IOException, TemplateException
    {
        emailGrade( enrollmentDao.getEnrollment( enrollmentId ) );
        return "redirect:/section/grade?enrollmentId=" + enrollmentId;
    }

    @RequestMapping(value = "/section/email", params = "sectionId")
    public String emailGrades( @RequestParam Long sectionId )
        throws IOException, TemplateException
    {
        Section section = sectionDao.getSection( sectionId );
        for( Enrollment enrollment : section.getEnrollments() )
            emailGrade( enrollment );
        return "redirect:/section/roster?id=" + sectionId;
    }

}
