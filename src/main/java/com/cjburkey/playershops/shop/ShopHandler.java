package com.cjburkey.playershops.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.inventory.ItemStack;
import com.cjburkey.playershops.IO;
import com.cjburkey.playershops.Util;

public final class ShopHandler {
	
	private static final List<PlayerShop> shops = new ArrayList<>();
	
	public static boolean createShop(UUID ply) {
		if (hasShop(ply)) {
			return false;
		}
		shops.add(new PlayerShop(ply, new HashMap<ItemStack, ShopItemData>()));
		return save();
	}
	
	public static boolean deleteShop(UUID ply) {
		PlayerShop shop = getShop(ply);
		if (shop == null) {
			return false;
		}
		IO.deleteShop(ply);
		shops.remove(shop);
		return save();
	}
	
	public static boolean hasShop(UUID ply) {
		return getShop(ply) != null;
	}
	
	public static PlayerShop getShop(UUID ply) {
		boolean loaded = load();
		if (!loaded) {
			Util.log("Shops couldn't be loaded.");
			return null;
		}
		for (PlayerShop shop : shops) {
			if (shop.getOwner().equals(ply)) {
				return shop;
			}
		}
		return null;
	}
	
	public static boolean save() {
		for (PlayerShop shop : shops) {
			boolean worked = IO.writeShop(shop);
			if (worked) {
				Util.log("Saved shop for: " + shop.getOwner());
				continue;
			}
			Util.log("Couldn't save for: " + shop.getOwner());
		}
		return true;
	}
	
	public static boolean load() {
		shops.clear();
		PlayerShop[] lShops = IO.getAllShops();
		if (lShops == null) {
			return false;
		}
		for (PlayerShop p : lShops) {
			shops.add(p);
			Util.log("Loaded shop for: " + p.getOwner());
		}
		return true;
	}
	
}