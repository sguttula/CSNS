package csns.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import csns.model.academics.dao.CourseDao;

@Controller
public class CourseController {

    @Autowired
    private CourseDao courseDao;

    @RequestMapping("/course/search")
    public String search( @RequestParam(required = false) String text,
        ModelMap models )
    {
        if( StringUtils.hasText( text ) ) models.addAttribute( "courses",
            courseDao.searchCourses( text, true, -1 ) );

        return "course/search";
    }

    @RequestMapping("/course/view")
    public String view( @RequestParam Long id, ModelMap models )
    {
        models.put( "course", courseDao.getCourse( id ) );
        return "course/view";
    }

}
