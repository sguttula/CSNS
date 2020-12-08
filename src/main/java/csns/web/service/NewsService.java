package csns.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import csns.model.academics.Department;
import csns.model.academics.dao.DepartmentDao;
import csns.model.news.News;
import csns.model.news.dao.NewsDao;

@RestController
public class NewsService {

    @Autowired
    private NewsDao newsDao;

    @Autowired
    private DepartmentDao departmentDao;

    @RequestMapping("/service/department/{dept}/news")
    public List<News> list( @PathVariable String dept, ModelMap models )
    {
        Department department = departmentDao.getDepartment( dept );
        return newsDao.getNews( department );
    }

}
