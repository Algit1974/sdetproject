package testclasses;

import java.util.Map;

import pageclasses.ProductItem;

public class BaseTest2 extends BaseTest {
       
	public int getTotalNumberOfItemsInMap(Map<ProductItem, Integer> itemsMap) {
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
	public Map<ProductItem, Integer> mergeUpdateCartItemsMaps(Map<ProductItem , Integer> map1, Map<ProductItem , Integer> map2) {
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
