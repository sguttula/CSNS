package csns.model.academics.dao;

import java.util.List;

import csns.model.academics.Department;
import csns.model.academics.Term;
import csns.model.core.User;

public interface TermDao {

    /**
     * Returns the terms in which the instructor taught any class.
     */
    List<Term> getTermsByInstructor( User instructor );

    /**
     * Returns the terms in which the student took any class.
     */
    List<Term> getTermsByStudent( User student );

    /**
     * Returns the term in which the user evaluated any rubric as an external
     * evaluator.
     */
    List<Term> getTermsByEvaluator( User evaluator );

    /**
     * Returns the terms in which there were any sections for the department.
     */
    List<Term> getSectionTerms( Department department );

}
