package csns.test;

import java.util.Scanner;

import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

@Test
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
public class Setup extends AbstractTransactionalTestNGSpringContextTests {

    @Override
    @BeforeSuite
    protected void springTestContextPrepareTestInstance() throws Exception
    {
        super.springTestContextPrepareTestInstance();
    }

    @BeforeSuite(alwaysRun = true,
        dependsOnMethods = { "springTestContextPrepareTestInstance" })
    public void init()
    {
        executeSqlScript( "classpath:csns-create.sql" );
        executeSqlScript( "classpath:csns-test-insert.sql", false );
    }

    @AfterSuite(alwaysRun = true)
    public void cleanup()
    {
        executeSqlScript( "classpath:csns-drop.sql", false );
    }

    private void executeSqlScript( String path )
    {
        try
        {
            StringBuilder sb = new StringBuilder();
            Resource resource = applicationContext.getResource( path );
            Scanner in = new Scanner( resource.getFile() );
            while( in.hasNextLine() )
            {
                sb.append( in.nextLine() );
                sb.append( "\n" );
            }
            in.close();
            jdbcTemplate.update( sb.toString() );
        }
        catch( Exception e )
        {
            throw new RuntimeException( e );
        }
    }

}
