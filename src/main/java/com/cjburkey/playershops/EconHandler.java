package com.cjburkey.playershops;

import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import com.cjburkey.playershops.shop.PlayerShop;
import com.cjburkey.playershops.shop.ShopItemData;
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
	
	public static void tryBuy(Player ply, ItemStack stack, int amt, PlayerShop shop) {
		stack = new ItemStack(stack);
		stack.setAmount(amt);
		ShopItemData dat = shop.getData(stack);
		Util.log(stack);
		if (dat.getBuy() < 0.0d) {
			Util.msg(ply, "&cBuying is disabled for this item in this shop.");
			return;
		}
		if (dat.getStock() < amt) {
			Util.msg(ply, "&cNot enough items in stock.");
			return;
		}
		if (dat.getBuy() > 0.0d && !take(ply.getUniqueId(), amt * dat.getBuy())) {
			Util.msg(ply, "&cYou do not have enough money.");
			return;
		}
		if (dat.getBuy() > 0.0d && !give(shop.getOwner(), amt * dat.getBuy())) {
			Util.msg(ply, "&cFailed to give money to shop owner, refunding you.");
			give(ply.getUniqueId(), amt * dat.getBuy());
			return;
		}
		ply.getInventory().addItem(stack);
	}
	
	public static void trySell(Player ply, ItemStack stack, int amt, PlayerShop shop) {
		stack = new ItemStack(stack);
		stack.setAmount(amt);
		ShopItemData dat = shop.getData(stack);
		if (dat.getSell() < 0.0d) {
			Util.msg(ply, "&cSelling is disabled for this item in this shop.");
			return;
		}
		if (!ply.getInventory().containsAtLeast(stack, amt)) {
			Util.msg(ply, "&cYou do not have enough of this item.");
			return;
		}
		if (dat.getSell() > 0.0d) {
			if (!take(shop.getOwner(), amt * dat.getSell())) {
				Util.msg(ply, "&cThe shop owner does not have enough money to pay you.");
				return;
			}
			if (!give(ply.getUniqueId(), amt * dat.getSell())) {
				Util.msg(ply, "&cFailed to transfer money to shop owner, refunding shop owner.");
				give(shop.getOwner(), amt * dat.getSell());
				return;
			}
		}
		ply.getInventory().removeItem(stack);
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