package com.cjburkey.playershops.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public interface IInvScreen {
	
	void open();
	void click(InventoryClickEvent e);
	Inventory getInv();
	Player getOpener();
	
}