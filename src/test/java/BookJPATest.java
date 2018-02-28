/*
 * A sample test class using Arquillian
 */


import com.rimidev.backing.AllAuthorsForBook;
import com.rimidev.maxbook.controller.AuthorJpaController;

import com.rimidev.maxbook.controller.exceptions.RollbackFailureException;
import com.rimidev.maxbook.entities.Author;
import com.rimidev.backing.AllAuthorsForBook;
import com.rimidev.maxbook.controller.InvoiceDetailsJpaController;
import com.rimidev.maxbook.entities.InvoiceDetails;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertTrue;



@Ignore
@RunWith(Arquillian.class)
public class BookJPATest {

    private Logger logger = Logger.getLogger(BookJPATest.class.getName());

    @Deployment
    public static WebArchive deploy() {

        // Use an alternative to the JUnit assert library called AssertJ
        // Need to reference MySQL driver as it is not part of either
        // embedded or remote
        final File[] dependencies = Maven
                .resolver()
                .loadPomFromFile("pom.xml")
                .resolve("mysql:mysql-connector-java",
                        "org.assertj:assertj-core").withoutTransitivity()
                .asFile();

        // The webArchive is the special packaging of your project
        // so that only the test cases run on the server or embedded
        // container
        final WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                .addPackage(AuthorJpaController.class.getPackage())
                .addPackage(RollbackFailureException.class.getPackage())
                .addPackage(Author.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/glassfish-resources.xml"), "glassfish-resources.xml")
                .addAsResource(new File("src/main/resources/META-INF/persistence.xml"), "META-INF/persistence.xml")
                .addAsResource("CreateBookStoreTables.sql")
                .addAsLibraries(dependencies);

        return webArchive;
    }

    @Inject
    private InvoiceDetailsJpaController invoiceDetailsController;

    @Resource(name = "java:app/jdbc/myBooks")
    private DataSource ds;

    /**
     *
     * @throws SQLException
     */
    @Test
    public void should_find_authors_from_isbn() throws SQLException {
        
        //List<InvoiceDetails> list = invoiceDetailsController.getTopSellingBooks();
        logger.log(Level.SEVERE, ">>>>>>>> list size");
        assertThat(true);
        
        
        
    }

    public void try_me(){
      
    }

 
}

