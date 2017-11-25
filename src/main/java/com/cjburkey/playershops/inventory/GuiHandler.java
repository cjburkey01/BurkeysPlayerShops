package com.cjburkey.playershops.inventory;

import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import com.cjburkey.playershops.gui.GuiShop;

public class GuiHandler {
	
	private static final Map<Player, GuiShop> shops = new LinkedHashMap<>();
	
	public static final void open(GuiShop shop) {
		shops.put(shop.getOpener(), shop);
		shop.open();
		shop.getOpener().openInventory(shop.getInv());
	}
	
	public static void close(Player ply) {
		if (isOpen(ply)) {
			shops.remove(ply);
			ply.closeInventory();
		}
	}
	
	public static boolean isOpen(Player ply) {
		return get(ply) != null;
	}
	
	public static GuiShop get(Player ply) {
		return shops.get(ply);
	}
	
}