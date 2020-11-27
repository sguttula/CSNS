package csns.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import csns.model.academics.Course;
import csns.model.academics.Term;
import csns.model.academics.Section;
import csns.model.academics.dao.CourseDao;
import csns.model.academics.dao.SectionDao;
import csns.model.site.Announcement;
import csns.model.site.Block;
import csns.model.site.Item;
import csns.model.site.Site;
import csns.model.site.dao.AnnouncementDao;
import csns.model.site.dao.BlockDao;
import csns.model.site.dao.ItemDao;
import csns.model.site.dao.SiteDao;
import csns.security.SecurityUtils;

@Controller
public class SiteBlockController {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private SectionDao sectionDao;

    @Autowired
    private SiteDao siteDao;

    @Autowired
    private BlockDao blockDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private AnnouncementDao announcementDao;

    private static final Logger logger = LoggerFactory
        .getLogger( SiteBlockController.class );

    private Section getSection( String qtr, String cc, int sn )
    {
        Term term = new Term();
        term.setShortString( qtr );
        Course course = courseDao.getCourse( cc );
        return sectionDao.getSection( term, course, sn );
    }

    @RequestMapping("/site/{qtr}/{cc}-{sn}/block/list")
    public String list( @PathVariable String qtr, @PathVariable String cc,
        @PathVariable int sn, ModelMap models )
    {
        Section section = getSection( qtr, cc, sn );
        models.put( "section", section );
        return "site/block/list";
    }

    @RequestMapping("/site/{qtr}/{cc}-{sn}/block/toggle")
    @ResponseStatus(HttpStatus.OK)
    public void toggle( @PathVariable String qtr, @PathVariable String cc,
        @PathVariable int sn, @RequestParam Long id )
    {
        Site site = getSection( qtr, cc, sn ).getSite();
        String action = site.getBlock( id ).toggle() ? "hided" : "unhided";
        site = siteDao.saveSite( site );

        logger.info( SecurityUtils.getUser().getUsername() + " " + action
            + " block " + id + " in site " + site.getId() );
    }

    @RequestMapping("/site/{qtr}/{cc}-{sn}/block/remove")
    public String remove( @PathVariable String qtr, @PathVariable String cc,
        @PathVariable int sn, @RequestParam Long id )
    {
        Site site = getSection( qtr, cc, sn ).getSite();
        site.removeBlock( id );
        site = siteDao.saveSite( site );

        logger.info( SecurityUtils.getUser().getUsername() + " removed block "
            + id + " from site " + site.getId() );

        return "redirect:list";
    }

    @RequestMapping("/site/{qtr}/{cc}-{sn}/block/reorder")
    @ResponseStatus(HttpStatus.OK)
    public void reorder( @PathVariable String qtr, @PathVariable String cc,
        @PathVariable int sn, @RequestParam Long id,
        @RequestParam int newIndex )
    {
        Site site = getSection( qtr, cc, sn ).getSite();
        Block block = site.removeBlock( id );
        if( block != null )
        {
            site.getBlocks().add( newIndex, block );
            site = siteDao.saveSite( site );
        }

        logger.info( SecurityUtils.getUser().getUsername() + " moved block "
            + id + " in site " + site.getId() + " to position " + newIndex );
    }

    @RequestMapping("/site/{qtr}/{cc}-{sn}/block/toggleItem")
    @ResponseStatus(HttpStatus.OK)
    public void toggleItem( @RequestParam Long blockId,
        @RequestParam Long itemId )
    {
        Block block = blockDao.getBlock( blockId );
        String action = block.getItem( itemId ).toggle() ? "hided" : "unhided";
        block = blockDao.saveBlock( block );

        logger.info( SecurityUtils.getUser().getUsername() + " " + action
            + " item " + itemId + " in block " + blockId );
    }

    @RequestMapping("/site/{qtr}/{cc}-{sn}/block/removeItem")
    public String removeItem( @RequestParam Long blockId,
        @RequestParam Long itemId )
    {
        Block block = blockDao.getBlock( blockId );
        Item item = block.removeItem( itemId );
        block = blockDao.saveBlock( block );
        item.delete();
        itemDao.saveItem( item );

        logger.info( SecurityUtils.getUser().getUsername() + " removed item "
            + itemId + " from block " + blockId );

        return "redirect:list";
    }

    @RequestMapping("/site/{qtr}/{cc}-{sn}/block/reorderItem")
    @ResponseStatus(HttpStatus.OK)
    public void reorderItem( @PathVariable String qtr, @PathVariable String cc,
        @PathVariable int sn, @RequestParam Long blockId,
        @RequestParam Long itemId, @RequestParam int newIndex )
    {
        Block block = blockDao.getBlock( blockId );
        Item item = block.removeItem( itemId );
        if( item != null )
        {
            block.getItems().add( newIndex, item );
            block = blockDao.saveBlock( block );
        }

        logger.info( SecurityUtils.getUser().getUsername() + " moved item "
            + itemId + " in block " + blockId + " to position " + newIndex );
    }

    @RequestMapping("/site/{qtr}/{cc}-{sn}/block/removeAnnouncement")
    public String removeAnnouncement( @RequestParam Long id )
    {
        Announcement announcement = announcementDao.getAnnouncement( id );
        Site site = announcement.getSite();
        announcement.setSite( null );
        announcement = announcementDao.saveAnnouncement( announcement );

        logger.info( SecurityUtils.getUser().getUsername()
            + " removed announcement " + id + " from site " + site.getId() );

        return "redirect:list";
    }

}
