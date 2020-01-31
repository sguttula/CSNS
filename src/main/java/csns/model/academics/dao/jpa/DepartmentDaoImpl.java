package csns.model.academics.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.academics.Department;
import csns.model.academics.dao.DepartmentDao;

@Repository
public class DepartmentDaoImpl implements DepartmentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Department getDepartment( Long id )
    {
        return entityManager.find( Department.class, id );
    }

    @Override
    public Department getDepartment( String abbreviation )
    {
        List<Department> departments = entityManager.createQuery(
            "from Department where abbreviation = :abbreviation",
            Department.class )
            .setParameter( "abbreviation", abbreviation.toLowerCase() )
            .getResultList();
        return departments.size() == 0 ? null : departments.get( 0 );
    }

    @Override
    public Department getDepartmentByName( String name )
    {
        List<Department> departments = entityManager.createQuery(
            "from Department where name = :name", Department.class )
            .setParameter( "name", name )
            .getResultList();
        return departments.size() == 0 ? null : departments.get( 0 );
    }

    @Override
    public Department getDepartmentByFullName( String fullName )
    {
        List<Department> departments = entityManager.createQuery(
            "from Department where fullName = :fullName", Department.class )
            .setParameter( "fullName", fullName )
            .getResultList();
        return departments.size() == 0 ? null : departments.get( 0 );
    }

    @Override
    public List<Department> getDepartments()
    {
        return entityManager.createQuery( "from Department order by id asc",
            Department.class ).getResultList();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DEPT_ADMIN_' + #department.abbreviation)")
    public Department saveDepartment( Department department )
    {
        return entityManager.merge( department );
    }

}
