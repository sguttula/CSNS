package csns.model.academics.dao;

import java.util.List;

import csns.model.academics.Course;
import csns.model.academics.Department;
import csns.model.academics.Term;
import csns.model.academics.Section;
import csns.model.assessment.Rubric;
import csns.model.core.User;

public interface SectionDao {

    Section getSection( Long id );

    Section getSection( Term term, Course course, int number );

    /**
     * A special section is a section without an instructor. We use a special
     * section to hold the bulk-imported grades that don't belong to an actual
     * CSNS section.
     */
    Section getSpecialSection( Term term, Course course );

    List<Section> getSections( Department department, Term term );

    List<Section> getSections( Course course, Integer beginYear,
        Integer endYear );

    List<Section> getSectionsByInstructor( User instructor, Term term );

    List<Section> getSectionsByInstructor( User instructor, Term term,
        Course course );

    List<Section> getSectionsByStudent( User student, Term term );

    List<Section> getSectionsByEvaluator( User evaluator, Term term );

    List<Section> getSectionsByRubric( Rubric rubric );

    List<Section> searchSections( String text, int maxResults );

    Section addSection( Term term, Course course, User instructor );

    Section deleteSection( Section section );

    Section saveSection( Section section );

}
