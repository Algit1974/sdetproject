package testclasses;


import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageclasses.HomePage;
import pageclasses.ProductItem;

public class AddProductOnHomePage extends BaseTest {
     
	private static final Logger log = LogManager.getLogger(AddProductOnHomePage.class.getName());
	
	@Test ()
	public void addProductOnHomepage() {
		HomePage homepage = new HomePage(driver); 
		
		Map<ProductItem, Integer> initialItemsInCart = homepage.getItemsInCart();
		log.info("Initial items in Cart: " + getTotalNumberOfItemsInMap(initialItemsInCart));
		
	      //homepage.mouseOverItem().selectSize().selectColor().clickAddToCart();;   
	     // homepage.mouseOverItem("Radiant Tee").selectSize("S").selectColor("Blue").clickAddToCart(); 
		  homepage.mouseOverItem("Breathe-Easy Tank").selectSize("M").selectColor("Purple").clickAddToCart();
		  //homepage.mouseOverItem("Breathe-Easy Tank").selectSize("M").selectColor("Purple").clickAddToCart();
		//  homepage.mouseOverItem("Breathe-Easy Tank").selectSize("S").selectColor("Yellow").clickAddToCart();
		//  homepage.mouseOverItem("Push It Messenger Bag").clickAddToCart();
		//  homepage.mouseOverItem("Push It Messenger Bag").selectSize("M").selectColor("Blue").clickAddToCart();
		//homepage.mouseOverItem().selectSize().selectColor().clickAddToCart();
	   // homepage.mouseOverItem("Radiant Tee").clickAddToCart();
		
		//homepage.mouseOverItem("Push It Messenger Bag").selectSize("M").clickAddToCart();
		//homepage.mouseOverItem("Push It Messenger Bag").selectColor("Blue").clickAddToCart();
		driver.navigate().refresh();
	
		Map<ProductItem, Integer> itemsAddedInCurrentSession = homepage.getItemsAddedInCurrentSession();
		
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
				
		Map<ProductItem, Integer> actualUpdatedCart = homepage.getItemsInCart();
		log.info("Total number of Items in actual cart: " + getTotalNumberOfItemsInMap(actualUpdatedCart));
		
		
		if(!actualUpdatedCart.isEmpty()) {
		   log.info("The following items are in actual cart.");
		   for(ProductItem item:actualUpdatedCart.keySet()) {
		   log.info(item.name() + " " + item.size() + " " + item.color() + " " + "Quantity:  " + actualUpdatedCart.get(item));
			
		}
	} 
	   	
		Assert.assertEquals(actualUpdatedCart, expectedUpdatedCart);
	}

	public static int getTotalNumberOfItemsInMap(Map<ProductItem, Integer> itemsMap) {
		int totalNumberOfItems = 0;
		for(ProductItem item:itemsMap.keySet()) {
			totalNumberOfItems += itemsMap.get(item);
		}
		return totalNumberOfItems;
	}
	
	int getnumberOfItemTypesInCart(Map<ProductItem, Integer> itemsMap) {
		return itemsMap.size();
	}
	
	/*
	 updateMap method: This method takes two maps as input.
     Iteration: The method iterates over the entries in map2.
     merge method: For each entry in map2, it uses the merge method on map1.
     If the key exists in map1, the merge method adds the corresponding values from both maps.
     If the key doesn't exist in map1, the entry from map2 is simply added to map1.
	 */
	public static Map<ProductItem, Integer> mergeUpdateCartItemsMaps(Map<ProductItem , Integer> map1, Map<ProductItem , Integer> map2) {
		 for(Map.Entry<ProductItem , Integer> entry:map2.entrySet()){
			map1.merge(entry.getKey(), entry.getValue(), Integer::sum);
			//OR we can do the following
			// ProductItem key = entry.getKey();
	        // Integer value = entry.getValue();
			//map1.merge(key, value, Integer::sum); 
		}
		 return map1;
	}
}
