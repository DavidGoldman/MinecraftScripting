package scripting.wrapper.world;

import net.minecraft.item.Item;

public class ScriptItem {
	
	public final Item item;
	
	private ScriptItem(Item item) {
		this.item = item;
	}
	
	public int getItemID() {
		return Item.getIdFromItem(item);
	}
	
	public String getItemName() {
		return Item.itemRegistry.getNameForObject(item);
	}
	
	public static ScriptItem forName(String name) {
		Item item = null;
		if (Item.itemRegistry.containsKey(name))
			item = (Item)Item.itemRegistry.getObject(name); 
		else {
			try { item = Item.getItemById(Integer.parseInt(name)); }
			catch(NumberFormatException e) { }
		}
		return fromItem(item);
	}
	
	public static ScriptItem forID(int id) {
		return fromItem(Item.getItemById(id));
	}
	
	public static ScriptItem fromItem(Item item) {
		return (item == null) ? null : new ScriptItem(item);
	}
}
