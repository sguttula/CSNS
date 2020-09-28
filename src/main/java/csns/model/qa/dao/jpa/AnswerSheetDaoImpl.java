package csns.model.qa.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import csns.model.qa.AnswerSheet;
import csns.model.qa.ChoiceQuestion;
import csns.model.qa.RatingQuestion;
import csns.model.qa.dao.AnswerSheetDao;

@Repository
public class AnswerSheetDaoImpl implements AnswerSheetDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AnswerSheet> findAnswerSheets( ChoiceQuestion choiceQuestion,
        Integer selection )
    {
        String query = "select answerSheet from AnswerSheet answerSheet "
            + "join answerSheet.sections section "
            + "join section.answers answer "
            + "join answer.selections selection "
            + "where answer.question = :question and selection = :selection "
            + "order by answerSheet.date asc";

        return entityManager.createQuery( query, AnswerSheet.class )
            .setParameter( "question", choiceQuestion )
            .setParameter( "selection", selection )
            .getResultList();
    }

    @Override
    public List<AnswerSheet> findAnswerSheets( RatingQuestion ratingQuestion,
        Integer rating )
    {
        String query = "select answerSheet from AnswerSheet answerSheet "
            + "join answerSheet.sections section "
            + "join section.answers answer "
            + "where answer.question = :question and answer.rating = :rating "
            + "order by answerSheet.date asc";

        return entityManager.createQuery( query, AnswerSheet.class )
            .setParameter( "question", ratingQuestion )
            .setParameter( "rating", rating )
            .getResultList();
    }

}
