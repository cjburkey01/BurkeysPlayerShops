package com.cjburkey.playershops;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class LanguageHandler {
	
	private static final Map<String, String> loc = new HashMap<>();
	
	private static FileConfiguration conf;
	
	public static void init(JavaPlugin plugin, File langFile) {
		if (!langFile.exists()) {
			plugin.saveResource("lang.yml", false);
			Util.log("Creating default language file.");
		}
		conf = YamlConfiguration.loadConfiguration(langFile);
		conf.options().copyDefaults(true);
		try {
			conf.save(langFile);
		} catch (IOException e) {
			Util.log("Failed to save lang file.");
			e.printStackTrace();
		}
	}
	
	public static void load() {
		Util.log("Loading language settings.");
		Set<String> keys = conf.getKeys(false);
		loc.clear();
		for (String key : keys) {
			loc.put(key, conf.getString(key));
		}
	}
	
	public static String get(String key) {
		if (loc.containsKey(key)) {
			return loc.get(key);
		}
		return key;
	}
	
	public static String getFormat(String key, Object... values) {
		if (loc.containsKey(key)) {
			return String.format(loc.get(key), values);
		}
		return key;
	}
	
}