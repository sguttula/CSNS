package csns.web.controller;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import csns.model.academics.Department;
import csns.model.academics.dao.DepartmentDao;
import csns.model.news.News;
import csns.model.news.dao.NewsDao;
import csns.security.SecurityUtils;

@Controller
public class NewsController {

    @Autowired
    private NewsDao newsDao;

    @Autowired
    private DepartmentDao departmentDao;

    private static final Logger logger = LoggerFactory.getLogger( NewsController.class );

    @RequestMapping("/department/{dept}/news/current")
    public String current( @PathVariable String dept, ModelMap models )
    {
        Department department = departmentDao.getDepartment( dept );
        models.put( "user", SecurityUtils.getUser() );
        models.put( "newses", newsDao.getNews( department ) );
        return "news/current";
    }

    @RequestMapping("/department/{dept}/news/delete")
    public String delete( @PathVariable String dept, @RequestParam Long id )
    {
        News news = newsDao.getNews( id );
        news.setExpireDate( Calendar.getInstance() );
        news = newsDao.saveNews( news );

        logger.info( SecurityUtils.getUser().getUsername() + " deleted news "
            + news.getId() );

        return "redirect:/department/" + dept + "/news/current";
    }

}
