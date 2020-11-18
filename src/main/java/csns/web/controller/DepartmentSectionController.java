package csns.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import csns.helper.GradeSheet;
import csns.model.academics.Course;
import csns.model.academics.Department;
import csns.model.academics.Enrollment;
import csns.model.academics.Term;
import csns.model.academics.Section;
import csns.model.academics.dao.DepartmentDao;
import csns.model.academics.dao.TermDao;
import csns.model.academics.dao.SectionDao;
import csns.model.core.User;
import csns.web.editor.CoursePropertyEditor;
import csns.web.editor.TermPropertyEditor;

@Controller
public class DepartmentSectionController {

    @Autowired
    private SectionDao sectionDao;

    @Autowired
    private TermDao termDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private WebApplicationContext context;

    @InitBinder
    public void initBinder( WebDataBinder binder )
    {
        binder.registerCustomEditor( Term.class,
            (TermPropertyEditor) context.getBean( "termPropertyEditor" ) );
        binder.registerCustomEditor( Course.class,
            (CoursePropertyEditor) context.getBean( "coursePropertyEditor" ) );
    }

    @RequestMapping("/department/{dept}/sections")
    public String sections( @PathVariable String dept,
        @RequestParam(required = false) Term term, ModelMap models )
    {
        Department department = departmentDao.getDepartment( dept );
        List<Term> terms = termDao.getSectionTerms( department );

        Term currentTerm = new Term();
        if( term == null ) term = currentTerm;
        if( !terms.contains( currentTerm ) ) terms.add( 0, currentTerm );
        Term nextTerm = currentTerm.next();
        if( !terms.contains( nextTerm ) ) terms.add( 0, nextTerm );

        models.put( "department", department );
        models.put( "term", term );
        models.put( "terms", terms );
        models.put( "sections", sectionDao.getSections( department, term ) );
        return "department/sections";
    }

    @RequestMapping("/department/{dept}/section")
    public String section( @PathVariable String dept, @RequestParam Long id,
        ModelMap models )
    {
        Section section = sectionDao.getSection( id );
        models.put( "department", departmentDao.getDepartment( dept ) );
        models.put( "gradeSheet", new GradeSheet( section ) );
        return "section/view";
    }

    @RequestMapping("/department/{dept}/section/grades")
    public String grades( @RequestParam Term term, @RequestParam Course course,
        @RequestParam int number, HttpServletResponse response )
        throws IOException
    {
        Section section = sectionDao.getSection( term, course, number );
        if( section != null )
        {
            List<Enrollment> enrollments = section.getEnrollments();
            Collections.sort( enrollments );
            response.setContentType( "text/plain" );
            PrintWriter out = response.getWriter();
            for( int i = 0; i < enrollments.size(); ++i )
            {
                User student = enrollments.get( i ).getStudent();
                String grade = enrollments.get( i ).getGrade() == null ? ""
                    : enrollments.get( i ).getGrade().getSymbol();
                out.println( (i + 1) + " " + student.getCin() + " "
                    + student.getLastName() + "," + student.getFirstName() + " "
                    + grade );
            }
        }
        return null;
    }

}
