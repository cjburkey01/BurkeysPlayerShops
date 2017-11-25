package com.cjburkey.playershops.inventory;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class InvGuiItem {
	
	private ItemStack stack;
	private ClickEvent e;
	
	public InvGuiItem(ItemStack stack, ClickEvent e) {
		this.stack = stack;
		this.e = e;
	}
	
	public void click(ClickType click) {
		e.click(click);
	}
	
	public ItemStack getStack() {
		return stack;
	}
	
}