package testclasses;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class BaseTest {
  WebDriver driver;
  Properties prop;
  
  private static final Logger log = LogManager.getLogger(BaseTest.class.getName());
  
@BeforeTest
@Parameters({"OS", "browser"})
public void setup(String os, String browser) throws IOException, URISyntaxException {
	
	FileReader file = new FileReader("./src//test//resources//config.properties");
	prop = new Properties();
	prop.load(file);
	
	if(prop.getProperty("execution_env").trim().equalsIgnoreCase("remote")) {	
		 DesiredCapabilities capabilities = new DesiredCapabilities();
		
		 if(os.equalsIgnoreCase("windows")) {
			// System.out.println("Setting platform to windows");
			 capabilities.setPlatform(Platform.WIN11);
	     } 
		 else if(os.equalsIgnoreCase("linux")) {
			 capabilities.setPlatform(Platform.LINUX);
		 }
		 else if(os.equalsIgnoreCase("mac")) {
			 capabilities.setPlatform(Platform.MAC);
		 }
		 else {
			 log.error("No matching os: " + os);
			 return;
		 }
		 log.info("Platfom set to: " + capabilities.getPlatformName());
		
		 switch(browser.toLowerCase()) {
			case "chrome":  capabilities.setBrowserName("chrome"); break;
			case "firefox": capabilities.setBrowserName("firefox"); break;
			case "edge":    capabilities.setBrowserName("MicrosoftEdge"); break;
			default: log.error("Invalid browser name."); 
			return;       //exit the setup method if browser is invalid
	     }
		   log.info("Browser name set to: " + capabilities.getBrowserName());
		   driver = new RemoteWebDriver(new URI("http://localhost:4444/wd/hub").toURL(), capabilities);   //URL(String) constructor deprecated
	}

	  if(prop.getProperty("execution_env").trim().equalsIgnoreCase("local")) {
		switch(browser.toLowerCase()) {
		case "chrome": driver = new ChromeDriver(); break;
		case "firefox": driver = new FirefoxDriver(); break;
		case "edge":  driver = new EdgeDriver(); break;
		default: log.error("Invalid browser name."); 
		return;     //exit the setup method if browser name is invalid
	     }
	    }
	
	//driver.manage().deleteAllCookies();
	driver.get("https://magento.softwaretestingboard.com/");
	JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("return window.stop();");
	driver.manage().window().maximize();
	
 }

@AfterTest
public void tearDown() {
	driver.quit();
 }
}

