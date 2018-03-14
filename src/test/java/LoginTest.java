
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginTest {

    private Logger logger = Logger.getLogger(LoginTest.class.getName());

    @Test
    public void get_members() throws InterruptedException, AWTException {
        System.setProperty("webdriver.chrome.driver", "C:\\dev\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:8080/MaxBook/login.xhtml");
        Thread.sleep(1000);
        WebElement loginBox = driver.findElement(By.id("loginform:login-username"));
        loginBox.sendKeys("test@test.com");

        //register-password
        Thread.sleep(1000); 
        WebElement passwordBox = driver.findElement(By.id("loginform:login-password"));
        passwordBox.sendKeys("password");

        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.delay(2000);
        
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        

       

       
    }
}
