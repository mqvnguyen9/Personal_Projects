package items;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {

	private final HashMap<String, ArrayList<Item>> itemMap;
	
	public Inventory() {
		itemMap = new HashMap<>();
	}
	
	public void addItem(Item item) {
		itemMap.computeIfAbsent(item.getName(), k -> new ArrayList<>());
		itemMap.get(item.getName()).add(item);
	}
	
	public void removeItem(Item item) {
		if (itemMap.get(item.getName()) == null) {
			return;
		}
		itemMap.get(item.getName()).remove(item);
		if (itemMap.get(item.getName()).size() == 0) {
			itemMap.remove(item.getName());
		}
	}
	
	public HashMap<String, ArrayList<Item>> getItemMap() {
		return itemMap;
	}
	
	public Item getItem(String itemName) {
		return itemMap.get(itemName).get(0);
	}
	
	public int getQuantityOf(String itemName) {
		return itemMap.get(itemName).size();
	}
}
