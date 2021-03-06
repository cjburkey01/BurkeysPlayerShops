package com.cjburkey.playershops.shop;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.inventory.ItemStack;
import com.cjburkey.playershops.Util;

public final class PlayerShop {
	
	private final UUID owner;
	private final Map<ItemStack, ShopItemData> items;
	
	public PlayerShop(UUID owner, Map<ItemStack, ShopItemData> items) {
		this.owner = owner;
		this.items = items;
	}
	
	public UUID getOwner() {
		return owner;
	}
	
	public Set<Entry<ItemStack, ShopItemData>> getItems() {
		return items.entrySet();
	}
	
	public boolean addQuantity(ItemStack in) {
		if (!hasItem(in)) {
			return false;
		}
		ShopItemData data = getData(in);
		if (data == null) {
			return false;
		}
		setAmount(in, in.getAmount() + data.getStock());
		in.setAmount(0);
		return true;
	}
	
	public ShopItemData setData(ItemStack stack, ShopItemData data) {
		return items.put(stack, data);
	}
	
	public boolean addItem(ItemStack stack, double buy, double sell) {
		if (hasItem(stack)) {
			return false;
		}
		ItemStack add = new ItemStack(stack);
		add.setAmount(1);
		boolean worked = setData(add, new ShopItemData(buy, sell)) == null;
		organize();
		return worked;
	}
	
	public void setAmount(ItemStack stack, int amt) {
		if (!hasItem(stack)) {
			return;
		}
		ItemStack already = getStoredStack(stack);
		ShopItemData data = getData(already);
		if (data == null) {
			return;
		}
		data.setStock(amt);
		setData(already, data);
	}
	
	public boolean removeItem(ItemStack stack) {
		if (!hasItem(stack)) {
			return false;
		}
		for (Entry<ItemStack, ShopItemData> entry : items.entrySet()) {
			if (entry.getKey().isSimilar(stack)) {
				items.remove(entry.getKey());
				return true;
			}
		}
		return false;
	}
	
	public boolean hasItem(ItemStack stack) {
		for (Entry<ItemStack, ShopItemData> entry : items.entrySet()) {
			if (entry.getKey().isSimilar(stack)) {
				return true;
			}
		}
		return false;
	}
	
	public ShopItemData getData(ItemStack stack) {
		for (Entry<ItemStack, ShopItemData> entry : items.entrySet()) {
			if (entry.getKey().isSimilar(stack)) {
				return entry.getValue();
			}
		}
		return null;
	}
	
	public ItemStack getStoredStack(ItemStack in) {
		for (Entry<ItemStack, ShopItemData> entry : items.entrySet()) {
			if (entry.getKey().isSimilar(in)) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	public void organize() {
		Util.log("Organizing shop");
		List<Entry<ItemStack, ShopItemData>> s = items.entrySet().stream().sorted(new Comparator<Entry<ItemStack, ShopItemData>>() {
			public int compare(Entry<ItemStack, ShopItemData> o1, Entry<ItemStack, ShopItemData> o2) {
				return o1.getKey().getType().compareTo(o2.getKey().getType());
			}
		}).collect(Collectors.toList());
		items.clear();
		for (Entry<ItemStack, ShopItemData> entry : s) {
			items.put(entry.getKey(), entry.getValue());
		}
	}
	
	// Start:	inclusive
	// End:		exclusive
	public Map<ItemStack, ShopItemData> getItems(int start, int end) {
		Map<ItemStack, ShopItemData> out = new LinkedHashMap<>();
		List<ItemStack> keys = new ArrayList<>(items.keySet());
		List<ShopItemData> vals = new ArrayList<>(items.values());
		end = Integer.min(items.size(), end);
		for (int i = start; i < end; i ++) {
			out.put(keys.get(i), vals.get(i));
		}
		return out;
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