package csns.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import csns.model.academics.Course;
import csns.model.academics.CourseMapping;
import csns.model.academics.Department;
import csns.model.academics.dao.CourseDao;
import csns.model.academics.dao.CourseMappingDao;
import csns.model.academics.dao.DepartmentDao;
import csns.security.SecurityUtils;

@Controller
@SessionAttributes("mapping")
public class CourseMappingControllerS {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private CourseMappingDao courseMappingDao;

    private static final Logger logger = LoggerFactory
        .getLogger( CourseMappingControllerS.class );

    @RequestMapping("/department/{dept}/course/mapping/addCourse")
    @ResponseBody
    public ResponseEntity<String> addCourse(
        @ModelAttribute("mapping") CourseMapping mapping,
        @RequestParam Long courseId, @RequestParam String group )
    {
        Course course = courseDao.getCourse( courseId );
        if( mapping.contains( course ) )
            return new ResponseEntity<String>( HttpStatus.BAD_REQUEST );

        if( group.equalsIgnoreCase( "group1" ) )
            mapping.getGroup1().add( course );
        else
            mapping.getGroup2().add( course );

        logger.info( SecurityUtils.getUser().getUsername() + " added course "
            + courseId + " to mapping " + mapping.getId() );

        return new ResponseEntity<String>( HttpStatus.OK );
    }

    @RequestMapping("/department/{dept}/course/mapping/removeCourse")
    @ResponseBody
    public void removeCourse( @ModelAttribute("mapping") CourseMapping mapping,
        @RequestParam Long courseId )
    {
        mapping.removeCourse( courseId );
        logger.info( SecurityUtils.getUser().getUsername() + " removed course "
            + courseId + " from mapping " + mapping.getId() );
    }

    @RequestMapping(value = "/department/{dept}/course/mapping/create",
        method = RequestMethod.GET)
    public String create( @PathVariable String dept, ModelMap models )
    {
        Department department = departmentDao.getDepartment( dept );
        CourseMapping mapping = new CourseMapping( department );
        models.put( "department", department );
        models.put( "mapping", mapping );
        return "course/mapping/create";
    }

    @RequestMapping(value = "/department/{dept}/course/mapping/create",
        method = RequestMethod.POST)
    public String create( @ModelAttribute("mapping") CourseMapping mapping,
        SessionStatus sessionStatus )
    {
        // Only save the mapping if both groups are not empty.
        if( mapping.getGroup1().size() > 0 && mapping.getGroup2().size() > 0 )
        {
            mapping = courseMappingDao.saveCourseMapping( mapping );
            logger.info( SecurityUtils.getUser().getUsername()
                + " added course mapping " + mapping.getId() );
        }

        sessionStatus.setComplete();
        return "redirect:list";
    }

    @RequestMapping(value = "/department/{dept}/course/mapping/edit",
        method = RequestMethod.GET)
    public String edit( @RequestParam Long id, ModelMap models )
    {
        CourseMapping mapping = courseMappingDao.getCourseMapping( id );
        models.put( "department", mapping.getDepartment() );
        models.put( "mapping", mapping );
        return "course/mapping/edit";
    }

    @RequestMapping(value = "/department/{dept}/course/mapping/edit",
        method = RequestMethod.POST)
    public String edit( @ModelAttribute("mapping") CourseMapping mapping,
        SessionStatus sessionStatus )
    {
        mapping = courseMappingDao.saveCourseMapping( mapping );
        logger.info( SecurityUtils.getUser().getUsername()
            + " edited course mapping " + mapping.getId() );

        sessionStatus.setComplete();
        return "redirect:list";
    }

}
