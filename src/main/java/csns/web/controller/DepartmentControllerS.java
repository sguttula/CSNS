package csns.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.WebApplicationContext;

import csns.model.academics.Department;
import csns.model.academics.dao.DepartmentDao;
import csns.model.core.User;
import csns.model.core.dao.UserDao;
import csns.model.forum.Forum;
import csns.model.mailinglist.Mailinglist;
import csns.model.wiki.Page;
import csns.model.wiki.Revision;
import csns.model.wiki.dao.RevisionDao;
import csns.security.SecurityUtils;
import csns.web.editor.UserPropertyEditor;
import csns.web.validator.DepartmentValidator;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Controller
@SessionAttributes("department")
public class DepartmentControllerS {

    @Autowired
    private UserDao userDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private RevisionDao revisionDao;

    @Autowired
    private DepartmentValidator departmentValidator;

    @Autowired
    private Configuration freemarkerConfiguration;

    @Autowired
    private WebApplicationContext context;

    private static final Logger logger = LoggerFactory
        .getLogger( DepartmentControllerS.class );

    @InitBinder
    public void initBinder( WebDataBinder binder )
    {
        binder.registerCustomEditor( User.class,
            (UserPropertyEditor) context.getBean( "userPropertyEditor" ) );
    }

    @RequestMapping(value = "/admin/department/add", method = RequestMethod.GET)
    public String add( ModelMap models )
    {
        models.put( "department", new Department() );
        return "department/add";
    }

    @RequestMapping(value = "/admin/department/add",
        method = RequestMethod.POST)
    public String add( @ModelAttribute Department department,
        BindingResult bindingResult, SessionStatus sessionStatus )
        throws IOException, TemplateException
    {
        departmentValidator.validate( department, bindingResult );
        if( bindingResult.hasErrors() ) return "department/add";

        department = departmentDao.saveDepartment( department );
        sessionStatus.setComplete();

        logger.info( SecurityUtils.getUser().getUsername()
            + " added department " + department.getAbbreviation() );

        String adminRole = "ROLE_DEPT_ADMIN_" + department.getAbbreviation();
        for( User user : department.getAdministrators() )
        {
            user.getRoles().add( adminRole );
            userDao.saveUser( user );
        }

        createForums( department );
        createMailinglists( department );
        createWikiPages( department );

        return "redirect:/admin/department/list";
    }

    private void createForums( Department department )
    {
        String names[] = { "Announcements", "Advisement", "Job Opportunities",
            "General Discussion" };

        for( String name : names )
        {
            Forum forum = new Forum( name );
            forum.getModerators().addAll( department.getAdministrators() );
            forum.setDepartment( department );
            department.getForums().add( forum );
        }

        department = departmentDao.saveDepartment( department );
        logger.info( department.getAbbreviation() + " forums created." );
    }

    private void createMailinglists( Department department )
    {
        String names[] = { "students", "undergrads", "grads", "grads-g0",
            "grads-g1", "grads-g2", "grads-g3", "alumni", "alumni-undergrad",
            "alumni-grad" };
        String descriptions[] = { "All the students in the department.",
            "Undergradudate students in the department.",
            "Graduate students in the department.",
            "Graduate students with G0 standing (Incoming).",
            "Graduate students with G1 standing (Conditionally Classified).",
            "Graduate students with G2 standing (Classified).",
            "Graduate students with G3 standing (Candidacy).",
            "All the alumni of the department.",
            "Alumni of the undergraduate program.",
            "Alumni of the graduate program." };

        for( int i = 0; i < names.length; ++i )
        {
            Mailinglist mailinglist = new Mailinglist();
            mailinglist
                .setName( department.getAbbreviation() + "-" + names[i] );
            mailinglist.setDescription( descriptions[i] );
            mailinglist.setDepartment( department );
            department.getMailinglists().add( mailinglist );
        }

        department = departmentDao.saveDepartment( department );
        logger.info( department.getAbbreviation() + " mailing lists created." );
    }

    private void createWikiPages( Department department )
        throws IOException, TemplateException
    {
        String paths[] = {
            "/wiki/content/department/" + department.getAbbreviation() + "/",
            "/wiki/content/department/" + department.getAbbreviation()
                + "/sidebar" };
        String subjects[] = { department.getName() + " Department Wiki",
            department.getName() + " Department Wiki Sidebar" };
        String fTemplates[] = { "wiki.department.home.txt",
            "wiki.department.sidebar.txt" };
        Map<String, Object> fModels = new HashMap<String, Object>();
        fModels.put( "department", department );

        for( int i = 0; i < paths.length; ++i )
        {
            Revision revision = revisionDao.getRevision( paths[i] );
            revision = revision == null ? new Revision( new Page( paths[i] ) )
                : revision.clone();

            Page page = revision.getPage();
            page.setLocked( false );
            page.setPassword( "" );
            page.setOwner( department.getAdministrators().get( 0 ) );

            revision.setAuthor( department.getAdministrators().get( 0 ) );
            revision.setIncludeSidebar( true );
            revision.setSubject( subjects[i] );
            revision
                .setContent( FreeMarkerTemplateUtils.processTemplateIntoString(
                    freemarkerConfiguration.getTemplate( fTemplates[i] ),
                    fModels ) );

            revisionDao.saveRevision( revision );
        }

        logger.info( department.getAbbreviation() + " wiki pages created." );
    }

    @RequestMapping(value = "/admin/department/edit",
        method = RequestMethod.GET)
    public String edit( @RequestParam Long id, ModelMap models )
    {
        models.put( "department", departmentDao.getDepartment( id ) );
        return "department/edit";
    }

    @RequestMapping(value = "/admin/department/edit",
        method = RequestMethod.POST)
    public String edit( @ModelAttribute Department department,
        BindingResult bindingResult, SessionStatus sessionStatus )
    {
        departmentValidator.validate( department, bindingResult );
        if( bindingResult.hasErrors() ) return "admin/department/edit";

        departmentDao.saveDepartment( department );
        sessionStatus.setComplete();

        logger.info( SecurityUtils.getUser().getUsername()
            + " edited department " + department.getAbbreviation() );

        return "redirect:/admin/department/list";
    }

}
