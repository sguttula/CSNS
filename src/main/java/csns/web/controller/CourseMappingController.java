package csns.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import csns.model.academics.CourseMapping;
import csns.model.academics.Department;
import csns.model.academics.dao.CourseMappingDao;
import csns.model.academics.dao.DepartmentDao;
import csns.security.SecurityUtils;

@Controller
public class CourseMappingController {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private CourseMappingDao courseMappingDao;

    private static final Logger logger = LoggerFactory.getLogger( CourseMappingController.class );

    @RequestMapping("/department/{dept}/course/mapping/list")
    public String list( @PathVariable String dept, ModelMap models )
    {
        Department department = departmentDao.getDepartment( dept );
        models.put( "department", department );
        models.put( "mappings", courseMappingDao.getCourseMappings( department ) );
        return "course/mapping/list";
    }

    @RequestMapping("/department/{dept}/course/mapping/delete")
    public String delete( @RequestParam Long id )
    {
        CourseMapping mapping = courseMappingDao.getCourseMapping( id );
        mapping.setDeleted( true );
        mapping = courseMappingDao.saveCourseMapping( mapping );

        logger.info( SecurityUtils.getUser().getUsername()
            + " deleted course mapping " + mapping.getId() );

        return "redirect:list";
    }

}
