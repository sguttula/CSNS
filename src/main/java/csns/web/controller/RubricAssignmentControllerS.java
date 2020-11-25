package csns.web.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.WebApplicationContext;

import csns.model.academics.Department;
import csns.model.academics.Section;
import csns.model.academics.dao.SectionDao;
import csns.model.assessment.Rubric;
import csns.model.assessment.RubricAssignment;
import csns.model.assessment.dao.RubricAssignmentDao;
import csns.model.assessment.dao.RubricDao;
import csns.model.core.User;
import csns.model.core.dao.UserDao;
import csns.security.SecurityUtils;
import csns.web.editor.CalendarPropertyEditor;
import csns.web.editor.RubricPropertyEditor;
import csns.web.validator.RubricAssignmentValidator;

@Controller
@SessionAttributes({ "assignment", "rubrics" })
public class RubricAssignmentControllerS {

    @Autowired
    private RubricDao rubricDao;

    @Autowired
    private RubricAssignmentDao rubricAssignmentDao;

    @Autowired
    private SectionDao sectionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RubricAssignmentValidator rubricAssignmentValidator;

    @Autowired
    private WebApplicationContext context;

    private static final Logger logger = LoggerFactory
        .getLogger( RubricAssignmentControllerS.class );

    @InitBinder
    public void initBinder( WebDataBinder binder )
    {
        binder.registerCustomEditor( Rubric.class,
            (RubricPropertyEditor) context.getBean( "rubricPropertyEditor" ) );
        binder.registerCustomEditor( Calendar.class,
            new CalendarPropertyEditor() );
    }

    private JSONArray toJSONArray( List<User> users )
    {
        JSONArray jsonArray = new JSONArray();
        for( User user : users )
        {
            Map<String, String> json = new HashMap<String, String>();
            json.put( "id", user.getId().toString() );
            json.put( "value", user.getName() );
            json.put( "label", user.getCin() + " " + user.getName() );
            jsonArray.put( json );
        }
        return jsonArray;
    }

    @RequestMapping(value = "/rubric/assignment/create",
        method = RequestMethod.GET)
    public String create( @RequestParam Long sectionId, ModelMap models )
    {
        User user = SecurityUtils.getUser();
        Section section = sectionDao.getSection( sectionId );
        Department department = section.getCourse().getDepartment();

        List<Rubric> rubrics = rubricDao
            .getPublishedDepartmentRubrics( department );
        rubrics.addAll( rubricDao.getPublishedPersonalRubrics( user ) );
        if( rubrics.size() == 0 )
        {
            models.put( "message", "error.rubric.no.rubric" );
            models.put( "backUrl", "/section/taught" );
            return "error";
        }

        RubricAssignment assignment = new RubricAssignment();
        assignment.setSection( section );
        assignment.setRubric( rubrics.get( 0 ) );
        assignment.setName( "Rubric: " + rubrics.get( 0 ).getName() );

        List<User> evaluators = department.getEvaluators();
        evaluators.removeAll( section.getInstructors() );

        models.put( "rubrics", rubrics );
        models.put( "assignment", assignment );
        models.put( "evaluators", toJSONArray( evaluators ) );
        return "rubric/assignment/create";
    }

    @RequestMapping(value = "/rubric/assignment/create",
        method = RequestMethod.POST)
    public String create(
        @ModelAttribute("assignment" ) RubricAssignment assignment,
        @RequestParam(value = "userId", required = false) Long ids[],
        BindingResult result, SessionStatus sessionStatus)
    {
        rubricAssignmentValidator.validate( assignment, result );
        if( result.hasErrors() ) return "rubric/assignment/create";

        assignment.setExternalEvaluators( userDao.getUsers( ids ) );
        assignment = rubricAssignmentDao.saveRubricAssignment( assignment );
        sessionStatus.setComplete();

        logger.info( SecurityUtils.getUser().getUsername()
            + " created rubric assignment " + assignment.getId() );

        return "redirect:/section/taught#section-"
            + assignment.getSection().getId();
    }

    @RequestMapping(value = "/rubric/assignment/edit",
        method = RequestMethod.GET)
    public String edit( @RequestParam Long id, ModelMap models )
    {
        RubricAssignment assignment = rubricAssignmentDao
            .getRubricAssignment( id );

        User user = SecurityUtils.getUser();
        Department department = assignment.getSection()
            .getCourse()
            .getDepartment();
        List<Rubric> rubrics = rubricDao
            .getPublishedDepartmentRubrics( department );
        rubrics.addAll( rubricDao.getPublishedPersonalRubrics( user ) );
        if( rubrics.size() == 0 )
        {
            models.put( "message", "error.rubric.no.rubric" );
            models.put( "backUrl", "/section/taught" );
            return "error";
        }

        List<User> evaluators = department.getEvaluators();
        evaluators.removeAll( assignment.getSection().getInstructors() );
        evaluators.removeAll( assignment.getExternalEvaluators() );

        models.put( "rubrics", rubrics );
        models.put( "assignment", assignment );
        models.put( "evaluators", toJSONArray( evaluators ) );
        return "rubric/assignment/edit";
    }

    @RequestMapping(value = "/rubric/assignment/edit",
        method = RequestMethod.POST)
    public String edit(
        @ModelAttribute("assignment" ) RubricAssignment assignment,
        @RequestParam(value = "userId", required = false) Long ids[],
        BindingResult result, SessionStatus sessionStatus)
    {
        rubricAssignmentValidator.validate( assignment, result );
        if( result.hasErrors() ) return "rubric/assignment/edit";

        assignment.setExternalEvaluators( userDao.getUsers( ids ) );
        assignment = rubricAssignmentDao.saveRubricAssignment( assignment );
        sessionStatus.setComplete();

        logger.info( SecurityUtils.getUser().getUsername()
            + " edited rubric assignment " + assignment.getId() );

        return "redirect:/section/taught#section-"
            + assignment.getSection().getId();
    }

}
