package csns.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import csns.model.academics.Course;
import csns.model.academics.Department;
import csns.model.academics.dao.CourseDao;
import csns.model.academics.dao.DepartmentDao;
import csns.model.core.User;
import csns.model.core.dao.UserDao;
import csns.security.SecurityUtils;

@Controller
public class DepartmentInfoController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private DepartmentDao departmentDao;

    private final static Logger logger = LoggerFactory
        .getLogger( DepartmentInfoController.class );

    @RequestMapping(value = "/department/{dept}/people")
    public String people( @PathVariable String dept, ModelMap models )
    {
        models.put( "department", departmentDao.getDepartment( dept ) );
        return "department/people";
    }

    @PreAuthorize("hasRole('ROLE_DEPT_ADMIN_' + #dept)")
    @RequestMapping(
        value = "/department/{dept}/personnel/{personnel}/{operation}")
    public String people( @PathVariable String dept,
        @PathVariable String personnel, @PathVariable String operation,
        @RequestParam Long userId )
    {
        Department department = departmentDao.getDepartment( dept );

        String role;
        List<User> users;
        switch( personnel )
        {
            case "admin":
                role = "ROLE_DEPT_ADMIN_" + dept;
                users = department.getAdministrators();
                break;
            case "faculty":
                role = "ROLE_DEPT_FACULTY_" + dept;
                users = department.getFaculty();
                break;
            case "instructor":
                role = "ROLE_DEPT_INSTRUCTOR_" + dept;
                users = department.getInstructors();
                break;
            case "evaluator":
                role = "ROLE_DEPT_EVALUATOR_" + dept;
                users = department.getEvaluators();
                break;
            case "reviewer":
                role = "ROLE_DEPT_REVIEWER_" + dept;
                users = department.getReviewers();
                break;
            default:
                logger.warn( "Invalid personnel type: " + personnel );
                return "redirect:/department/" + dept + "/people";
        }

        User user = userDao.getUser( userId );
        switch( operation )
        {
            case "add":
                if( !users.contains( user ) )
                {
                    users.add( user );
                    user.getRoles().add( role );
                    logger.info( SecurityUtils.getUser().getUsername()
                        + " added " + role + " to " + user.getUsername() );
                }
                break;
            case "remove":
                if( users.contains( user ) )
                {
                    users.remove( user );
                    user.getRoles().remove( role );
                    logger.info( SecurityUtils.getUser().getUsername()
                        + " removed " + role + " from " + user.getUsername() );
                }
                break;
            default:
                logger.warn( "Invalid operation type: " + operation );
                return "redirect:/department/" + dept + "/people";
        }

        userDao.saveUser( user );
        departmentDao.saveDepartment( department );

        return "redirect:/department/" + dept + "/people#" + personnel;
    }

    @RequestMapping(value = "/department/{dept}/courses")
    public String courses( @PathVariable String dept, ModelMap models )
    {
        models.put( "department", departmentDao.getDepartment( dept ) );
        return "department/courses";
    }

    @PreAuthorize("hasRole('ROLE_DEPT_ADMIN_' + #dept)")
    @RequestMapping(value = "/department/{dept}/course/{level}/{operation}")
    public String courses( @PathVariable String dept,
        @PathVariable String level, @PathVariable String operation,
        @RequestParam Long courseId )
    {
        Department department = departmentDao.getDepartment( dept );
        Course course = courseDao.getCourse( courseId );

        List<Course> courses;
        switch( level )
        {
            case "undergraduate":
                courses = department.getUndergraduateCourses();
                break;
            case "graduate":
                courses = department.getGraduateCourses();
                break;
            default:
                logger.warn( "Invalid course level: " + level );
                return "redirect:/department/" + dept + "/courses";
        }

        switch( operation )
        {
            case "add":
                if( !courses.contains( course ) ) courses.add( course );
                break;
            case "remove":
                if( courses.contains( course ) ) courses.remove( course );
                break;
            default:
                logger.warn( "Invalid operation type: " + operation );
                return "redirect:/department/" + dept + "/courses";
        }

        departmentDao.saveDepartment( department );

        return "redirect:/department/" + dept + "/courses#" + level;
    }

    @RequestMapping("/department/{dept}/option/{option}")
    public String option( @PathVariable String dept,
        @PathVariable String option, ModelMap models )
    {
        Department department = departmentDao.getDepartment( dept );
        models.put( "result", department.getOptions().contains( option ) );
        return "jsonView";
    }

}
