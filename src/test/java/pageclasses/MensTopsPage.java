package pageclasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class MensTopsPage extends HomePage {

	public MensTopsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
}
