package csns.model.academics.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import csns.model.academics.Grade;
import csns.model.academics.dao.GradeDao;

@Repository
public class GradeDaoImpl implements GradeDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Grade getGrade( Long id )
    {
        return entityManager.find( Grade.class, id );
    }

    @Override
    public Grade getGrade( String symbol )
    {
        List<Grade> grades = entityManager.createQuery(
            "from Grade where symbol = :symbol", Grade.class )
            .setParameter( "symbol", symbol.toUpperCase() )
            .getResultList();
        return grades.size() == 0 ? null : grades.get( 0 );
    }

    @Override
    public List<Grade> getGrades()
    {
        return entityManager.createQuery( "from Grade order by id asc",
            Grade.class ).getResultList();
    }

}
