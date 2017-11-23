package com.cjburkey.playershops;

import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public final class EconHandler {
	
	private static Economy econ;
	
	public static boolean init(JavaPlugin jp) {
		if (jp.getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = jp.getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}
	
	public static boolean take(UUID player, double amt) {
		Player p = PlayerShops.getInstance().getServer().getPlayer(player);
		EconomyResponse e = econ.withdrawPlayer(p, Math.abs(amt));
		return e.transactionSuccess();
	}
	
	public static boolean give(UUID player, double amt) {
		Player p = PlayerShops.getInstance().getServer().getPlayer(player);
		EconomyResponse e = econ.depositPlayer(p, Math.abs(amt));
		return e.transactionSuccess();
	}
	
	public static Economy getEconomy() {
		return econ;
	}
	
}