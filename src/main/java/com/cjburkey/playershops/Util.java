package com.cjburkey.playershops;

import java.text.NumberFormat;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public final class Util {
	
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
	
	public static Inventory openGui(Player player, int rows, String title) {
		return Bukkit.createInventory(player, rows * 9, color(title));
	}
	
	public static boolean itemsSame(ItemStack stack1, ItemStack stack2) {
		return stack1.isSimilar(stack2);
	}
	
}