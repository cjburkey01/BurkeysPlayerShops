package com.cjburkey.playershops.shop;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import org.bukkit.inventory.ItemStack;
import com.cjburkey.playershops.Util;

public final class PlayerShop {
	
	private final UUID owner;
	private final HashMap<ItemStack, ShopItemData> items;
	
	public PlayerShop(UUID owner, HashMap<ItemStack, ShopItemData> items) {
		this.owner = owner;
		this.items = items;
	}
	
	public UUID getOwner() {
		return owner;
	}
	
	public Set<Entry<ItemStack, ShopItemData>> getItems() {
		return items.entrySet();
	}
	
	public boolean addQuantity(ItemStack in, double buy, double sell) {
		if (!hasItem(in)) {
			return false;
		}
		ShopItemData data = getData(in);
		if (data == null) {
			return false;
		}
		data.setStock(data.getStock() + in.getAmount());
		in.setAmount(0);
		return true;
	}
	
	public boolean addItem(ItemStack stack, double buy, double sell) {
		if (hasItem(stack)) {
			return false;
		}
		return items.put(stack, new ShopItemData(buy, sell)) != null;
	}
	
	public boolean hasItem(ItemStack stack) {
		for (Entry<ItemStack, ShopItemData> entry : items.entrySet()) {
			if (Util.itemsSame(entry.getKey(), stack)) {
				return true;
			}
		}
		return false;
	}
	
	public ShopItemData getData(ItemStack stack) {
		for (Entry<ItemStack, ShopItemData> entry : items.entrySet()) {
			if (Util.itemsSame(entry.getKey(), stack)) {
				return entry.getValue();
			}
		}
		return null;
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		return result;
	}
	
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PlayerShop other = (PlayerShop) obj;
		if (items == null) {
			if (other.items != null) {
				return false;
			}
		} else if (!items.equals(other.items)) {
			return false;
		}
		if (owner == null) {
			if (other.owner != null) {
				return false;
			}
		} else if (!owner.equals(other.owner)) {
			return false;
		}
		return true;
	}
	
	public String toString() {
		return owner.toString();
	}
	
}