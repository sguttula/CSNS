package csns.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import csns.model.academics.Department;
import csns.model.academics.dao.DepartmentDao;
import csns.model.news.dao.NewsDao;

@Controller
public class IndexController {

    @Autowired
    private NewsDao newsDao;

    @Autowired
    private DepartmentDao departmentDao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index( ModelMap models )
    {
        models.addAttribute( "departments", departmentDao.getDepartments() );
        return "index";
    }

    @RequestMapping({ "/department/{dept}/", "/department/{dept}" })
    public String index( @PathVariable String dept, ModelMap models,
        HttpServletResponse response )
    {
        Department department = departmentDao.getDepartment( dept );
        if( department == null ) return "redirect:/";

        Cookie cookie = new Cookie( "default-dept", dept );
        cookie.setPath( "/" );
        cookie.setMaxAge( 100000000 );
        response.addCookie( cookie );

        models.addAttribute( "department", department );
        models.addAttribute( "newses", newsDao.getNews( department ) );
        return "department/index";
    }

}
