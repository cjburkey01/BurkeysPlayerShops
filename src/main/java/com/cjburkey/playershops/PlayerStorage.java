package com.cjburkey.playershops;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.regex.Pattern;
import org.bukkit.entity.Player;

public class PlayerStorage {
	
	private static final Map<String, UUID> joined = new LinkedHashMap<>();
	
	public static void onPlayerJoin(Player player) {
		if (hasPlayerJoined(player)) {
			return;
		}
		Util.log("New player joined: " + player.getName());
		joined.put(player.getName(), player.getUniqueId());
		save();
	}
	
	public static boolean hasPlayerJoined(Player player) {
		for (Entry<String, UUID> ply : joined.entrySet()) {
			if (ply.getValue().equals(player.getUniqueId())) {
				return true;
			}
		}
		return false;
	}
	
	public static String getName(UUID id) {
		for (Entry<String, UUID> ply : joined.entrySet()) {
			if (ply.getValue().equals(id)) {
				return ply.getKey();
			}
		}
		return null;
	}
	
	public static UUID getUUID(String name) {
		return joined.get(name);
	}
	
	public static List<String> getJoinedNames() {
		return new ArrayList<>(joined.keySet());
	}
	
	public static List<UUID> getJoinedUUIDs() {
		return new ArrayList<>(joined.values());
	}
	
	private static void save() {
		if (IO.playerStore.exists()) {
			IO.playerStore.delete();
		}
		StringBuilder out = new StringBuilder();
		for (Entry<String, UUID> ply : joined.entrySet()) {
			out.append(ply.getKey());
			out.append(';');
			out.append(';');
			out.append(';');
			out.append(ply.getValue().toString());
			out.append('\n');
		}
		FileWriter writer = null;
		try {
			writer = new FileWriter(IO.playerStore);
			writer.write(out.toString());
			writer.close();
		} catch (Exception e) {
			Util.log("Failed to write player storage.");
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (Exception e) {
					Util.log("Failed to close writer of player storage.");
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void load() {
		if (!IO.playerStore.exists()) {
			return;
		}
		BufferedReader reader = null;
		String l = "";
		try {
			reader = new BufferedReader(new FileReader(IO.playerStore));
			joined.clear();
			while ((l = reader.readLine()) != null) {
				String[] splt = l.split(Pattern.quote(";;;"));
				if (splt.length != 2) {
					continue;
				}
				joined.put(splt[0], UUID.fromString(splt[1]));
			}
			reader.close();
		} catch (Exception e) {
			Util.log("Failed to read player storage.");
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					Util.log("Failed to close reader of player storage.");
					e.printStackTrace();
				}
			}
		}
	}
	
}