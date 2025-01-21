package pageclasses;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    WebDriver driver;
   
 public BasePage() {
	 
 }
  public BasePage(WebDriver driver) {
	   this.driver = driver;
	   
	   PageFactory.initElements(driver, this);
   }
  
  protected WebElement scrollToElement(WebElement element) {
	  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	  return element;
  }
  
  protected void mouseOverElement(WebElement element) {
	  scrollToElement(element);
	  Actions actions = new Actions(driver);
      actions.moveToElement(element).perform();
  }
  
  protected void waitForElementToLoad(WebElement element, int time) {
	  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		 wait.until(ExpectedConditions.visibilityOf(element));
  }
  
  protected void waitForElementToBeClickable(WebElement element, int time) {
	  new WebDriverWait(driver, Duration.ofSeconds(time)).until(ExpectedConditions.elementToBeClickable(element));
  }
}
