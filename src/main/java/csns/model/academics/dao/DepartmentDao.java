package csns.model.academics.dao;

import java.util.List;

import csns.model.academics.Department;

public interface DepartmentDao {

    Department getDepartment( Long id );

    Department getDepartment( String abbreviation );

    Department getDepartmentByName( String name );

    Department getDepartmentByFullName( String fullName );

    List<Department> getDepartments();

    Department saveDepartment( Department department );

}
