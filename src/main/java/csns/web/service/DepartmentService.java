package csns.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import csns.model.academics.Department;
import csns.model.academics.dao.DepartmentDao;

@RestController
public class DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @RequestMapping(value = "/service/department/list")
    public List<Department> list( ModelMap models )
    {
        return departmentDao.getDepartments();
    }

}
