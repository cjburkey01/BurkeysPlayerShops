package com.cjburkey.playershops;

import java.text.NumberFormat;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Util {
	
	private static final Logger log = Logger.getLogger("Minecraft");
	
	public static final String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	public static final void msg(CommandSender to, Object msg) {
		to.sendMessage(color(msg + ""));
	}
	
	public static final void log(Object msg) {
		log.info(prepMsg(msg));
	}
	
	public static final String format(double i) {
		String better = EconHandler.getEconomy().format(i).replaceAll("[^\\d.]", "");
		return "$" + NumberFormat.getInstance().format(Double.parseDouble(better));
	}
	
	private static String prepMsg(Object msg) {
		String out = (msg == null) ? "null" : msg.toString();
		return String.format("[%s] %s", PlayerShops.getInstance().getDescription().getPrefix(), color(out));
	}
	
}