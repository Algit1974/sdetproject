package testclasses;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageclasses.HomePage;
import pageclasses.MensJacketsPage;
import pageclasses.ProductItem;

public class AddProductOnMensJacketsPage extends BaseTest2 {

	private static final Logger log = LogManager.getLogger(AddProductOnMensJacketsPage.class.getName());
    
	@Test
	public void addProductOnMensJacketsPage() throws InterruptedException {
		HomePage homepage = new HomePage(driver); 
    	MensJacketsPage jacketsPage = homepage.mouseOverMainMenuOption("Men").mouseOverSubMenuOption("Tops").mouseOverSubSubMenuOptionAndClick("Jackets");
      //System.out.println("Number of items on Mens Jackets Page: " + jacketsPage.getNumberOfItemsOnPage()); 
	   Map<ProductItem, Integer> initialItemsInCart = homepage.getItemsInCart();
	   log.info("Initial items in Cart: " + getTotalNumberOfItemsInMap(initialItemsInCart));
       jacketsPage.mouseOverItem().selectSize().selectColor().clickAddToCart();
	
	 //driver.navigate().refresh();
	Thread.sleep(3000);
       
	Map<ProductItem, Integer> itemsAddedInCurrentSession = jacketsPage.getItemsAddedInCurrentSession();
	
	log.info("Number of items added in current session: " + getTotalNumberOfItemsInMap(itemsAddedInCurrentSession));	
	
	if(!itemsAddedInCurrentSession.isEmpty()) {
	   log.info("The following items were added in current session");
	   for(ProductItem item:itemsAddedInCurrentSession.keySet()) {
		  log.info(item.name() + " " + item.size() + " " + item.color() + " " + "Quantity:  " + itemsAddedInCurrentSession.get(item));
	   }
    }
	
	Map<ProductItem, Integer> expectedUpdatedCart = mergeUpdateCartItemsMaps(initialItemsInCart, itemsAddedInCurrentSession);
	if(expectedUpdatedCart.isEmpty()) {
		log.info("There should be no items expected in the cart.");
	}
	else {
	log.info("The following items should be expected in the cart.");
	for(ProductItem item:expectedUpdatedCart.keySet()) {
		log.info(item.name() + " " + item.size() + " " + item.color() + " " + "Quantity:  " + expectedUpdatedCart.get(item));
	} 
}
			
	Map<ProductItem, Integer> actualUpdatedCart = jacketsPage.getItemsInCart();
	log.info("Total number of Items in actual cart: " + getTotalNumberOfItemsInMap(actualUpdatedCart));
	
	
	if(!actualUpdatedCart.isEmpty()) {
	   log.info("The following items are in actual cart.");
	   for(ProductItem item:actualUpdatedCart.keySet()) {
	   log.info(item.name() + " " + item.size() + " " + item.color() + " " + "Quantity:  " + actualUpdatedCart.get(item));
		
	}
} 
   	
	Assert.assertEquals(actualUpdatedCart, expectedUpdatedCart);
 }
}
