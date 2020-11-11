package csns.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import csns.model.academics.dao.DepartmentDao;

@Controller
public class DepartmentController {

    @Autowired
    private DepartmentDao departmentDao;

    @RequestMapping(value = "/admin/department/list")
    public String list( ModelMap models )
    {
        models.put( "departments", departmentDao.getDepartments() );
        return "department/list";
    }

}
