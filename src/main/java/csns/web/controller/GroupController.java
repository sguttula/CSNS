package csns.web.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import csns.model.core.Group;
import csns.model.core.Member;
import csns.model.core.User;
import csns.model.core.dao.GroupDao;
import csns.model.core.dao.MemberDao;
import csns.model.core.dao.UserDao;
import csns.security.SecurityUtils;

@Controller
public class GroupController {

    private static final Logger logger = LoggerFactory
        .getLogger( GroupController.class );

    @Autowired
    private UserDao userDao;

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private MemberDao memberDao;

    @Resource(name = "contentTypes")
    private Properties contentTypes;

    @RequestMapping("/department/{dept}/group/view")
    public String view( @RequestParam Long id, ModelMap models )
    {
        models.put( "group", groupDao.getGroup( id ) );
        return "group/view";
    }

    @RequestMapping("/department/{dept}/group/addUser")
    public String addUser( @RequestParam Long groupId,
        @RequestParam Long userId )
    {
        Group group = groupDao.getGroup( groupId );
        User user = userDao.getUser( userId );
        Member member = memberDao.getMember( group, user );
        if( member == null )
        {
            memberDao.saveMember( new Member( group, user ) );
            logger.info( SecurityUtils.getUser().getUsername() + " added user "
                + user.getUsername() + " to group " + group.getId() );

            group.setDate( new Date() );
            groupDao.saveGroup( group );
        }

        return "redirect:view?id=" + groupId;
    }

    @RequestMapping("/department/{dept}/group/removeMembers")
    public String removeMembers( @RequestParam Long groupId,
        @RequestParam("memberId") Long memberIds[] )
    {
        memberDao.deleteMembers( memberIds );
        logger.info( SecurityUtils.getUser().getUsername() + " deleted members "
            + Arrays.toString( memberIds ) + " from group " + groupId );

        Group group = groupDao.getGroup( groupId );
        group.setDate( new Date() );
        groupDao.saveGroup( group );

        return "redirect:view?id=" + groupId;
    }

    @RequestMapping("/department/{dept}/group/remove")
    public String remove( @PathVariable String dept, @RequestParam Long id )
    {
        Group group = groupDao.getGroup( id );
        group.setDepartment( null );
        group = groupDao.saveGroup( group );

        logger.info( SecurityUtils.getUser().getUsername() + " removed group "
            + group.getId() + " from department " + dept );

        return "redirect:/department/" + dept + "/people#group";
    }

    @RequestMapping("/department/{dept}/group/export")
    public String export( @RequestParam Long id, HttpServletResponse response )
        throws IOException
    {
        Group group = groupDao.getGroup( id );
        List<Member> members = group.getMembers();

        response.setContentType( contentTypes.getProperty( "xlsx" ) );
        response.setHeader( "Content-Disposition",
            "attachment; filename=\"" + group.getName() + ".xlsx\"" );

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet( "Users" );

        int n = members.size();
        Row row = sheet.createRow( 0 );
        row.createCell( 0 ).setCellValue( "CIN" );
        row.createCell( 1 ).setCellValue( "Last Name" );
        row.createCell( 2 ).setCellValue( "First Name" );
        row.createCell( 3 ).setCellValue( "Email" );
        row.createCell( 4 ).setCellValue( "Date Added" );

        DateFormat dateFormat = DateFormat.getDateInstance();
        for( int i = 0; i < n; i++ )
        {
            User user = members.get( i ).getUser();
            row = sheet.createRow( i + 1 );
            row.createCell( 0 ).setCellValue( user.getCin() );
            row.createCell( 1 ).setCellValue( user.getLastName() );
            row.createCell( 2 ).setCellValue( user.getFirstName() );
            row.createCell( 3 ).setCellValue( user.getPrimaryEmail() );
            row.createCell( 4 ).setCellValue(
                dateFormat.format( members.get( i ).getDate() ) );
        }

        wb.write( response.getOutputStream() );
        wb.close();

        return null;
    }

}
