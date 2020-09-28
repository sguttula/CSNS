package csns.model.qa.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import csns.model.qa.Question;
import csns.model.qa.dao.QuestionDao;

@Repository
public class QuestionDaoImpl implements QuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Question getQuestion( Long id )
    {
        return entityManager.find( Question.class, id );
    }

}
