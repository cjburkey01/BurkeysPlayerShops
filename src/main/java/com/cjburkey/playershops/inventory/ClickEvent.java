package com.cjburkey.playershops.inventory;

import org.bukkit.event.inventory.ClickType;

@FunctionalInterface
public interface ClickEvent {
	
	void click(ClickType click);
	
}