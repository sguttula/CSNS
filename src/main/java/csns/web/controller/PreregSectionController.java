package csns.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import csns.model.prereg.Schedule;
import csns.model.prereg.Section;
import csns.model.prereg.dao.SectionDao;
import csns.security.SecurityUtils;

@Controller
public class PreregSectionController {

    @Autowired
    private SectionDao sectionDao;

    private static final Logger logger = LoggerFactory
        .getLogger( PreregSectionController.class );

    @RequestMapping("/department/{dept}/prereg/section/remove")
    public String remove( @PathVariable String dept, @RequestParam Long id,
        ModelMap models )
    {
        Section section = sectionDao.getSection( id );
        if( section.getRegistrations().size() > 0 )
        {
            models.put( "message", "error.prereg.section.nonempty" );
            models.put( "backUrl", "/department/" + dept
                + "/prereg/registration/list?sectionId=" + id );
            return "error";
        }

        Schedule schedule = section.getSchedule();
        section.setSchedule( null );
        sectionDao.saveSection( section );
        logger.info( SecurityUtils.getUser().getUsername() + " removed section "
            + section.getId() );

        return "redirect:/department/" + dept + "/prereg/schedule/view?id="
            + schedule.getId();
    }

}
