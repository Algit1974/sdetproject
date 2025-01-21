package pageclasses;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HomePage extends BasePage {

	private WebElement currentItem; 
	private String selectedItemName;
	private String noSizeOption = "No size option";
	private String noColorOption = "No color option";
	private Map<ProductItem, Integer> cartItems = new HashMap<>();
	private Map<ProductItem, Integer> itemsAddedInCurrentSession = new HashMap<>();
	private Map<WebElement, String> sizeClickedForItem = new HashMap<>();  //To keep track of clicked size for an item
	private Map<WebElement, String> colorClickedForItem = new HashMap<>(); //To keep track of clicked color for an item
	
	//private Map<ProductItem, Pair<String, String>> sizeAndColorClickedForItem = new HashMap<>(); //This can be used to keep track of both
		
	private static final Logger log = LogManager.getLogger(HomePage.class.getName());
	
	public HomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

@FindBy(xpath = "//span[@class='counter-number']")
//@CacheLookup      
private WebElement cartIcon;

@FindBy(xpath = "//li[@class='product-item']")
private List<WebElement> itemsList;

private WebElement getElement(String itemName) {	
	 for(int i = 0; i<itemsList.size(); i++) {
	    if(itemsList.get(i).findElement(By.xpath(".//a[@class='product-item-link']")).getText().equals(itemName)) {
		  return itemsList.get(i);
	    }  
     } 
    
    return null; 
}

public HomePage mouseOverItem(String itemName) {
	//driver.switchTo().alert().dismiss();
	        WebElement element = getElement(itemName);
	        waitForElementToLoad(element, 5);
	        if(element == null) {
	        	System.out.println(itemName + " is not available on the homepage.");
	        }
	        else {
			mouseOverElement(element);
			currentItem = element;	
			selectedItemName = getItemName(element);
			log.info("moused over " + selectedItemName);
	        }
	return this;
}
  
public HomePage mouseOverItem() {
	 int randItemNo =  (int) (Math.random() * itemsList.size()) + 1;
	 currentItem = driver.findElement(By.xpath("//li[@class='product-item']["+randItemNo+"]"));
	 mouseOverElement(currentItem);
	 selectedItemName = currentItem.findElement(By.xpath(".//div[@class='product-item-details']//a")).getText();
	 log.info("Moused over " + selectedItemName);
	 return this;
}

public HomePage selectSize(String size) {
	String clickedSize = null;
	if(getSizeOptions(currentItem).size() == 0) {
		 clickedSize = noSizeOption;
		log.info("This item does not have any size options.  Size selection is ignored.");
	}
	else {
	  try {
	   WebElement sizeButton = currentItem.findElement(By.xpath(".//div[@option-label='"+size+"' ]")); //don't need this if no ads. 
	   scrollToElement(sizeButton);   //adds are covering this button so we need to scroll to it.
	   //currentItem.findElement(By.xpath(".//div[@option-label='"+size+"' ]")).click();   //can use this line instead of the above two lines if no adds
	   sizeButton.click();
	   clickedSize = size;
	   log.info("Clicked " + size + " size button.");
	 }
	 catch(NoSuchElementException e) {
		//e.printStackTrace();
		log.info("This item is not availabe in " + size + " size.");
	  }
    }  
	  sizeClickedForItem.put(currentItem, clickedSize); 
	return this;
  }


public HomePage selectSize() {
	  String clickedSize = null;
	  List<WebElement> sizeButtons = getSizeOptions(currentItem);
	  if(sizeButtons.size() == 0) {
		 clickedSize = noSizeOption;
		 log.info("No size options are available for this item.");
	 }
	 else {
		 int randIndex = new Random().nextInt(sizeButtons.size());
	     clickedSize = sizeButtons.get(randIndex).getAttribute("option-label");    //get random size
	     scrollToElement(sizeButtons.get(randIndex)).click();    //ads covering the button, so using scroll
	     sizeClickedForItem.put(currentItem, clickedSize);
	     log.info("Clicked " + clickedSize + " size button.");
	 }
	  sizeClickedForItem.put(currentItem, clickedSize);
	
	return this;
 }

private List<WebElement> getSizeOptions(WebElement item) {
	   return item.findElements(By.xpath(".//div[@class='swatch-option text']"));  //available sizes
}

public HomePage selectColor(String color) {
	String clickedColor = null;
	if(getColorOptions(currentItem).size() == 0) {
		clickedColor = noColorOption;
		log.info("This item does not have any color options.  Color selection is ignored.");
	}
	else {
		try {
	      WebElement colorButton = currentItem.findElement(By.xpath(".//div[@option-label='"+color+"']"));
	     //currentItem.findElement(By.xpath(".//div[@option-label='"+color+"']")).click();  //This statement is enough if no ads 
          ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", colorButton);
          colorButton.click();
          clickedColor = color;
	      log.info("Clicked " + color + " color button for " + selectedItemName);
		}
		catch(NoSuchElementException e) {
			log.info("This item is not availabe in " + color + " color.");		
		}
	}
		colorClickedForItem.put(currentItem, clickedColor);
	 return this;
 }

public HomePage selectColor() {
	
	String clickedColor = null;
	List<WebElement> colorButtons = getColorOptions(currentItem);
	if(colorButtons.size() == 0) {
		 clickedColor = noColorOption;
		 log.info("No color options are available for this item.");
	 }
	else {
		 int randIndex = new Random().nextInt(colorButtons.size());
		  //String randomColor = getRandomColorForItem(currentItem);
		 scrollToElement(colorButtons.get(randIndex)).click();     //ads covering the button, so have to scroll to it first
	     clickedColor = colorButtons.get(randIndex).getAttribute("option-label"); 
	   
	     log.info("Clicked " + clickedColor + " color button.");
	 }	
	colorClickedForItem.put(currentItem, clickedColor);
	return this;
  }

private List<WebElement> getColorOptions(WebElement item){
	return item.findElements(By.xpath(".//div[@class='swatch-option color']"));
}


public String getSelectedSizeForItem(String item) {
	WebElement element = getElement(item);
	  if(element == null) {
		  return "Item " + item + " is not avalaible on page.";
      }
	  else {
		   String selectedSize = getSelectedSize(element);
		  if(selectedSize.equals(null))
			  return "No size selected for " + item;
		  else return selectedSize;
	  }
}


public String getCurrentlySelectedItemSize() {
	if(currentItem == null) {
		log.error("No item is moused over currently.");
		return "No element selected at this time.";
	}	
	else
	{
		 String selectedSize = getSelectedSize(currentItem);
		  if(selectedSize.equals(null))
			  return "No size selected for " + getItemName(currentItem);
		  else return selectedSize;
	}		
}

private String getSelectedSize(WebElement element) {
	List<WebElement> selectedSizeButtons = element.findElements(By.xpath(".//div[@class='swatch-option text selected']"));
	  if(selectedSizeButtons.isEmpty())
		  return null;
	  else return selectedSizeButtons.getFirst().getAttribute("aria-label");
}

public String getColorSelectedForItem(String item) {
	  WebElement element = getElement(item);
		  if(element == null) {
			  return "Item " + item + " is not avalaible on page.";
	  }
		  else {
			    String selectedColor = getSelectedColor(element);
			    if(selectedColor.equals(null))
			    	return "No color selected for: " + item;
			    else return selectedColor;
		  }
}

public String getCurrentlySelectedItemColor() {
	if(currentItem == null) {
		log.error("No item is moused over currently.");
		return "No element selected at this time.";
	}
	else {
		String selectedSize = getSelectedColor(currentItem);
		if(selectedSize.equals(null)) 
			return "No color selected for: " + getItemName(currentItem);
		else
	       return getSelectedColor(currentItem);
	}
}

private String getSelectedColor(WebElement element) {
	 List<WebElement> selectedColorButtons = element.findElements(By.xpath(".//div[@class='swatch-option color selected']"));
	 if(selectedColorButtons.isEmpty()) 
   	  return null;
   	 else return selectedColorButtons.getFirst().getAttribute("aria-label");
}

private String getItemName(WebElement element) {
	return element.findElement(By.xpath(".//div[@class='product-item-details']//a")).getText();
}

//This method clicks "Add To Cart" button and updates the itemsAddedInCurrentSession map.  Item is added to this map only if all the options 
//for the are selected.  This map represents the items that are added in the current session only.
public void clickAddToCart() {
	  WebElement addButton = currentItem.findElement(By.xpath(".//button[@title='Add to Cart' ]"));  //ads covering the button
	   scrollToElement(addButton);  //so we need to scroll to it.
	 
	  JavascriptExecutor jse = (JavascriptExecutor) driver;
	  
	  if(getSizeOptions(currentItem).size() == 0 && getColorOptions(currentItem).size() == 0) {
		  addToCurrentSessionItems(new ProductItem(selectedItemName, noSizeOption, noColorOption));
	  }
	  
	  else if(getSizeOptions(currentItem).size() != 0 && getColorOptions(currentItem).size() != 0) {
		  if(getSelectedSize(currentItem) == null && getSelectedColor(currentItem) != null)
			  log.info("Size options available but NOT selected.");
		  if(getSelectedSize(currentItem) != null && getSelectedColor(currentItem) == null) 
			  log.info("Color options available but NOT selected.");
		  if(getSelectedSize(currentItem) != null && getSelectedColor(currentItem) != null)
			  addToCurrentSessionItems(new ProductItem(selectedItemName, sizeClickedForItem.get(currentItem), colorClickedForItem.get(currentItem)));
	  }       
	  
	  else if(getSizeOptions(currentItem).size() != 0 && getColorOptions(currentItem).size() == 0) {
		  if(getSelectedSize(currentItem) == null) 
			  log.info("Size options available but NOT selected.");
		  else 
			  addToCurrentSessionItems(new ProductItem(selectedItemName, sizeClickedForItem.get(currentItem), noColorOption));
		  }
	  
	  else if(getSizeOptions(currentItem).size()  == 0 && getColorOptions(currentItem).size() != 0) {
		  if(getSelectedColor(currentItem) == null)
			  log.info("Color options available but NOT selected.");
		  else
			  addToCurrentSessionItems(new ProductItem(selectedItemName, noSizeOption, colorClickedForItem.get(currentItem)));
	  }


	  jse.executeScript("arguments[0].click()", addButton);
	  //currentItem.findElement(By.xpath(".//button[@title='Add to Cart' ]")).click();  //this single statement is enough if no ads
	    
	   log.info("Clicked \"Add to Cart\" button.");
	}

//This method adds/updates item in the itemsAddedInCurrentSession map
private void addToCurrentSessionItems(ProductItem item) {
	if(itemsAddedInCurrentSession.containsKey(item)) {
		   itemsAddedInCurrentSession.replace(item, itemsAddedInCurrentSession.get(item), itemsAddedInCurrentSession.get(item)+1);
	   }
	   else
		   itemsAddedInCurrentSession.put(item, 1);
}
 
public String getConfirmationMessage() {
	return driver.findElement(By.xpath("//div[@role='alert']//div//div")).getText();
  }

public Map<ProductItem, Integer> getItemsInCart() {
	//waitForElementToLoad(cartIcon, 5);
	 if(cartIcon.getText().equals("")) {
		 log.info("Cart icon shows no items in cart.");
		//return Collections.emptyMap();  // this returns immutable map.
		return new HashMap<ProductItem, Integer>();   //We can also use return new HashMap<>();
	 }
	 
	//waitForElementToBeClickable(cartIcon, 5);
	 cartIcon.click();
	 log.info("Clicked cart icon.");
	 List<WebElement> itemElements = driver.findElements(By.xpath("//ol[@id='mini-cart']//li[@data-role='product-item']"));
	// System.out.println("Number of product types in cart: " + itemElements.size());
	 	
	 for(int i = 0; i < itemElements.size(); i++) {
		WebElement item = itemElements.get(i);
		String itemName = item.findElement(By.xpath(".//strong[@class='product-item-name']")).getText();
		//System.out.println("item name: " + itemName);
	    List<WebElement> options = item.findElements(By.tagName("dt"));    //size, color, or other options
	   // System.out.println("number of options: " + options.size());
	    String size = noSizeOption;
	    String color = noColorOption;
	    for(int j = 0; j < options.size(); j++) {
	    	//System.out.println(optionElem.getAttribute("innerText"));
	    	WebElement optionElem = options.get(j);
	    	switch(optionElem.getAttribute("innerText")) {
	    	case("Size"): size = optionElem.findElement(By.xpath(".//following-sibling::dd[1]/span")).getAttribute("innerText");
	    	       // System.out.println("Size: " + size);
	    	         break;
	    	case("Color"): color = optionElem.findElement(By.xpath(".//following-sibling::dd[1]/span")).getAttribute("innerText");
	    	       //System.out.println("Color: " + color);
	    	       break;
	    	default:  System.out.println("No options");
	    	}    	
	    }
	    int qty = Integer.parseInt(item.findElement(By.tagName("input")).getAttribute("data-item-qty"));
	    //System.out.println("Quantity: " + qty);
	    cartItems.put(new ProductItem(itemName, size, color), qty);	    
	 }	 
	return cartItems;	 	 
 }
public Map<ProductItem, Integer> getItemsAddedInCurrentSession() {
	   return itemsAddedInCurrentSession;
 }

}