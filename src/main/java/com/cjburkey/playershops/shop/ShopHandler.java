package com.cjburkey.playershops.shop;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import com.cjburkey.playershops.IO;
import com.cjburkey.playershops.LanguageHandler;
import com.cjburkey.playershops.PlayerStorage;
import com.cjburkey.playershops.Util;
import com.cjburkey.playershops.gui.GuiShop;
import com.cjburkey.playershops.inventory.GuiHandler;

public final class ShopHandler {
	
	public static final int SHOP_ROWS = 5;
	private static final List<PlayerShop> shops = new ArrayList<>();
	
	public static boolean createShop(UUID ply) {
		if (hasShop(ply)) {
			return false;
		}
		return shops.add(new PlayerShop(ply, new LinkedHashMap<ItemStack, ShopItemData>()));
	}
	
	public static boolean deleteShop(UUID ply) {
		PlayerShop shop = getShop(ply);
		if (shop == null) {
			return false;
		}
		IO.deleteShop(ply);
		return shops.remove(shop);
	}
	
	public static boolean hasShop(UUID ply) {
		return getShop(ply) != null;
	}
	
	public static PlayerShop getShop(UUID ply) {
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
	
	public static void showShop(UUID shopid, Player player, int page) {
		PlayerShop shop = ShopHandler.getShop(shopid);
		Inventory invShop = Util.createGui(player, SHOP_ROWS + 1, LanguageHandler.getFormat("guiTitle", PlayerStorage.getName(shopid)));
		GuiShop shopGui = new GuiShop(shop, invShop, player, page);
		GuiHandler.open(shopGui);
	}
	
}