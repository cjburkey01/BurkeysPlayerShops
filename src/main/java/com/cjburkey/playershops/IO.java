package com.cjburkey.playershops;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import com.cjburkey.playershops.shop.PlayerShop;
import com.cjburkey.playershops.shop.ShopItemData;

public final class IO {
	
	public static File pluginDir;
	public static File playerStore;
	public static File langFile;
	public static File shopDir;
	
	public static void init(PlayerShops p) {
		pluginDir = p.getDataFolder();
		playerStore = new File(pluginDir, "/players.txt");
		langFile = new File(pluginDir, "/lang.yml");
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
	
	public static void deleteShop(UUID player) {
		if (playerHasShop(player)) {
			getFileForPlayer(player).delete();
		}
	}
	
	public static PlayerShop readShop(UUID player) {
		if (!playerHasShop(player)) {
			Util.log("Player doesn't have shop: " + player);
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
	
	public static boolean writeShop(PlayerShop shop) {
		if (playerHasShop(shop.getOwner())) {
			getFileForPlayer(shop.getOwner()).delete();
		}
		FileWriter writer;
		try {
			writer = new FileWriter(getFileForPlayer(shop.getOwner()));
			writer.write(shopToString(shop));
			writer.close();
			return true;
		} catch(Exception e) {
		}
		return false;
	}
	
	public static PlayerShop[] getAllShops() {
		List<PlayerShop> shops = new ArrayList<>();
		File[] dir = shopDir.listFiles();
		for (File file : dir) {
			String uuid = file.getName().substring(0, file.getName().length() - 4);
			final UUID ply;
			try {
				ply = UUID.fromString(uuid);
			} catch (Exception e) {
				continue;
			}
			PlayerShop shop = readShop(ply);
			if (shop == null) {
				continue;
			}
			shops.add(shop);
		}
		return shops.toArray(new PlayerShop[shops.size()]);
	}
	
	private static PlayerShop shopFromString(UUID owner, String input) {
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
		
		List<?> tmp = conf.getList("items");
		if (tmp != null) {
			items = tmp.toArray(new ItemStack[tmp.size()]);
		}
		tmp = conf.getList("data");
		if (tmp != null) {
			data = tmp.toArray(new String[tmp.size()]);
		}
		
		if (items == null || data == null || items.length != data.length) {
			return null;
		}
		Map<ItemStack, ShopItemData> map = new LinkedHashMap<>();
		for (int i = 0; i < items.length; i ++) {
			map.put(items[i], ShopItemData.parse(data[i]));
		}
		return new PlayerShop(owner, map);
	}
	
	private static String shopToString(PlayerShop shop) {
		List<ItemStack> items = new ArrayList<>();
		List<String> data = new ArrayList<>();
		for (Entry<ItemStack, ShopItemData> entry : shop.getItems()) {
			items.add(entry.getKey());
			data.add(entry.getValue().toString());
		}
		return shopToString(items.toArray(new ItemStack[items.size()]), data.toArray(new String[data.size()]));
	}
	
	private static String shopToString(ItemStack[] items, String[] data) {
		YamlConfiguration conf = new YamlConfiguration();
		conf.set("items", items);
		conf.set("data", data);
		return conf.saveToString();
	}
	
}