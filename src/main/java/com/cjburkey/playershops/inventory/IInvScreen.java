package com.cjburkey.playershops.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface IInvScreen {
	
	void open();
	void click(InventoryClickEvent e);
	Inventory getInv();
	ItemStack atPos(int x, int y);
	Player getOpener();
	
}