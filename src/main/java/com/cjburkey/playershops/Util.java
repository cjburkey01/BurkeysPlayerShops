package com.cjburkey.playershops;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public final class Util {
	
	private static final Logger log = Logger.getLogger("Minecraft");
	
	public static final String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	public static final void msg(boolean prefix, CommandSender to, Object msg) {
		String msgOut = "";
		if (prefix) {
			msgOut = color(String.format("&f[&r%s&f]&r %s", LanguageHandler.get("prefix"), msg));
		} else {
			msgOut = color((msg == null) ? "null" : msg.toString());
		}
		to.sendMessage(msgOut);
	}
	
	public static final void log(Object msg) {
		log.info(prepMsg(msg));
	}
	
	public static final String format(double i) {
		return "$" + NumberFormat.getCurrencyInstance(Locale.getDefault()).format(i);
	}
	
	private static String prepMsg(Object msg) {
		String out = (msg == null) ? "null" : msg.toString();
		return String.format("[%s] %s", PlayerShops.getInstance().getDescription().getPrefix(), color(out));
	}
	
	public static Inventory createGui(Player player, int rows, String title) {
		return Bukkit.createInventory(player, rows * 9, color(title));
	}
	
}