package com.cjburkey.playershops.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import com.cjburkey.playershops.inventory.GuiHandler;

public class GuiEventHandler implements Listener {
	
	@EventHandler
	public void click(InventoryClickEvent e) {
		Player ply = (Player) e.getWhoClicked();
		if (!GuiHandler.isOpen(ply)) {
			return;
		}
		GuiShop shop = GuiHandler.get(ply);
		if (shop == null) {
			return;
		}
		shop.click(e);
	}
	
	@EventHandler
	public void close(InventoryCloseEvent e) {
		Player ply = (Player) e.getPlayer();
		if (!GuiHandler.isOpen(ply)) {
			return;
		}
		GuiShop shop = GuiHandler.get(ply);
		if (shop == null) {
			return;
		}
		GuiHandler.close(ply);
	}
	
}