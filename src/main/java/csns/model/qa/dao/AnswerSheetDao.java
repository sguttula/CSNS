package csns.model.qa.dao;

import java.util.List;

import csns.model.qa.AnswerSheet;
import csns.model.qa.ChoiceQuestion;
import csns.model.qa.RatingQuestion;

public interface AnswerSheetDao {

    List<AnswerSheet> findAnswerSheets( ChoiceQuestion choiceQuestion,
        Integer selection );

    List<AnswerSheet> findAnswerSheets( RatingQuestion ratingQuestion,
        Integer rating );

}
