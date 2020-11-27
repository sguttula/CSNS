package csns.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import csns.model.assessment.Rubric;
import csns.model.assessment.RubricIndicator;
import csns.model.assessment.dao.RubricDao;
import csns.security.SecurityUtils;
import csns.web.validator.RubricIndicatorValidator;
import csns.web.validator.RubricValidator;

@Controller
@SessionAttributes({ "rubric", "indicator" })
public class RubricControllerS {

    @Autowired
    private RubricDao rubricDao;

    @Autowired
    private RubricValidator rubricValidator;

    @Autowired
    private RubricIndicatorValidator rubricIndicatorValidator;

    private static final Logger logger = LoggerFactory
        .getLogger( RubricControllerS.class );

    @RequestMapping(value = "/department/{dept}/rubric/create",
        method = RequestMethod.GET)
    public String create( ModelMap models )
    {
        models.put( "rubric", new Rubric() );
        return "rubric/create";
    }

    @RequestMapping(value = "/department/{dept}/rubric/create",
        method = RequestMethod.POST)
    public String create( @ModelAttribute Rubric rubric, BindingResult result,
        SessionStatus sessionStatus )
    {
        rubricValidator.validate( rubric, result );
        if( result.hasErrors() ) return "rubric/create";

        rubric.setCreator( SecurityUtils.getUser() );
        rubric = rubricDao.saveRubric( rubric );

        logger.info( SecurityUtils.getUser().getUsername() + " created rubric "
            + rubric.getId() );

        sessionStatus.setComplete();
        return "redirect:view?id=" + rubric.getId();
    }

    @RequestMapping(value = "/department/{dept}/rubric/edit",
        method = RequestMethod.GET)
    public String edit( @RequestParam Long id, ModelMap models )
    {
        models.put( "rubric", rubricDao.getRubric( id ) );
        return "rubric/edit";
    }

    @RequestMapping(value = "/department/{dept}/rubric/edit",
        method = RequestMethod.POST)
    public String edit( @ModelAttribute Rubric rubric,
        @RequestParam(value = "next", required = false ) String next,
        BindingResult result, SessionStatus sessionStatus)
    {
        rubricValidator.validate( rubric, result );
        if( result.hasErrors() ) return "rubric/edit";

        rubric = rubricDao.saveRubric( rubric );

        logger.info( SecurityUtils.getUser().getUsername() + " edited rubric "
            + rubric.getId() );

        sessionStatus.setComplete();
        return "redirect:view?id=" + rubric.getId();
    }

    @RequestMapping(value = "/department/{dept}/rubric/addIndicator",
        method = RequestMethod.GET)
    public String addIndicator( @RequestParam Long id, ModelMap models )
    {
        models.put( "rubric", rubricDao.getRubric( id ) );
        models.put( "indicator", new RubricIndicator() );
        return "rubric/addIndicator";
    }

    @RequestMapping(value = "/department/{dept}/rubric/addIndicator",
        method = RequestMethod.POST)
    public String addIndicator(
        @ModelAttribute("indicator" ) RubricIndicator indicator,
        @RequestParam Long rubricId, BindingResult result,
        SessionStatus sessionStatus)
    {
        rubricIndicatorValidator.validate( indicator, result );
        if( result.hasErrors() ) return "rubric/addIndicator";

        Rubric rubric = rubricDao.getRubric( rubricId );
        if( !rubric.isPublished() )
        {
            rubric.getIndicators().add( indicator );
            rubric = rubricDao.saveRubric( rubric );
            logger.info( SecurityUtils.getUser().getUsername()
                + " added rubric indicator " + indicator.getId() );
        }

        sessionStatus.setComplete();
        return "redirect:view?id=" + rubricId;
    }

    @RequestMapping(value = "/department/{dept}/rubric/editIndicator",
        method = RequestMethod.GET)
    public String editIndicator( @RequestParam Long rubricId,
        @RequestParam Integer indicatorIndex, ModelMap models )
    {
        Rubric rubric = rubricDao.getRubric( rubricId );
        models.put( "rubric", rubric );
        models.put( "indicator", rubric.getIndicators().get( indicatorIndex ) );
        return "rubric/editIndicator";
    }

    @RequestMapping(value = "/department/{dept}/rubric/editIndicator",
        method = RequestMethod.POST)
    public String editIndicator(
        @ModelAttribute("indicator" ) RubricIndicator indicator,
        @RequestParam Long rubricId, @RequestParam Integer indicatorIndex,
        BindingResult result, SessionStatus sessionStatus)
    {
        rubricIndicatorValidator.validate( indicator, result );
        if( result.hasErrors() ) return "rubric/editIndicator";

        Rubric rubric = rubricDao.getRubric( rubricId );
        rubric.getIndicators().set( indicatorIndex, indicator );
        rubric = rubricDao.saveRubric( rubric );
        logger.info( SecurityUtils.getUser().getUsername()
            + " edited rubric indicator " + indicator.getId() );

        sessionStatus.setComplete();
        return "redirect:view?id=" + rubricId;
    }

    @RequestMapping("/department/{dept}/rubric/deleteIndicator")
    public String deleteIndicator( @RequestParam Long rubricId,
        @RequestParam int indicatorIndex )
    {
        Rubric rubric = rubricDao.getRubric( rubricId );
        rubric.getIndicators().remove( indicatorIndex );
        rubric = rubricDao.saveRubric( rubric );
        logger
            .info( SecurityUtils.getUser().getUsername() + " remove indicator #"
                + indicatorIndex + " from rubric " + rubricId );

        return "redirect:view?id=" + rubricId;
    }

}
