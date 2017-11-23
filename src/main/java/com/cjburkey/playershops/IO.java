package com.cjburkey.playershops;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import com.cjburkey.playershops.shop.PlayerShop;
import com.cjburkey.playershops.shop.ShopItemData;

public final class IO {
	
	public static File pluginDir;
	public static File shopDir;
	
	public static void init(PlayerShops p) {
		pluginDir = p.getDataFolder();
		shopDir = new File(pluginDir, "/data/");
		if (!shopDir.exists()) {
			shopDir.mkdirs();
		}
	}
	
	public static File getFileForPlayer(UUID ply) {
		return new File(shopDir, '/' + ply.toString() + ".yml");
	}
	
	public static boolean playerHasShop(UUID ply) {
		return getFileForPlayer(ply).exists();
	}
	
	public static PlayerShop getShopFromPlayer(UUID ply) {
		if (!playerHasShop(ply)) {
			return null;
		}
		return null;
	}
	
	public static PlayerShop getShop(UUID player) {
		if (!playerHasShop(player)) {
			return null;
		}
		BufferedReader reader = null;
		try {
			StringBuilder b = new StringBuilder();
			String line = null;
			reader = new BufferedReader(new FileReader(getFileForPlayer(player)));
			while ((line = reader.readLine()) != null) {
				b.append(line);
				b.append('\n');
			}
			reader.close();
			return shopFromString(player, b.toString());
		} catch (Exception e) {
		}
		return null;
	}
	
	public static boolean writeShop(UUID player, PlayerShop shop) {
		if (playerHasShop(player)) {
			getFileForPlayer(player).delete();
		}
		FileWriter writer;
		try {
			writer = new FileWriter(getFileForPlayer(player));
			writer.write(shopToString(shop));
			writer.close();
			return true;
		} catch(Exception e) {
		}
		return false;
	}
	
	public static PlayerShop shopFromString(UUID owner, String input) {
		YamlConfiguration conf = new YamlConfiguration();
		try {
			conf.loadFromString(input);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		ItemStack[] items = null;
		String[] data = null;
		if (!conf.contains("items") || !conf.contains("data")) {
			return null;
		}
		Object tmp = conf.get("items");
		if (tmp != null && tmp instanceof ItemStack[]) {
			items = (ItemStack[]) tmp;
		}
		tmp = conf.get("data");
		if (tmp != null && tmp instanceof String[]) {
			data = (String[]) tmp;
		}
		if (items == null || data == null || items.length != data.length) {
			return null;
		}
		HashMap<ItemStack, ShopItemData> map = new HashMap<>();
		for (int i = 0; i < items.length; i ++) {
			map.put(items[i], ShopItemData.parse(data[i]));
		}
		return new PlayerShop(owner, map);
	}
	
	public static String shopToString(PlayerShop shop) {
		List<ItemStack> items = new ArrayList<>();
		List<String> data = new ArrayList<>();
		for (Entry<ItemStack, ShopItemData> entry : shop.getItems()) {
			items.add(entry.getKey());
			data.add(entry.getValue().toString());
		}
		return shopToString(items.toArray(new ItemStack[items.size()]), data.toArray(new String[data.size()]));
	}
	
	public static String shopToString(ItemStack[] items, String[] data) {
		YamlConfiguration conf = new YamlConfiguration();
		conf.set("items", items);
		conf.set("data", data);
		return conf.saveToString();
	}
	
}