package com.cjburkey.playershops.cmds;

import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.cjburkey.playershops.LanguageHandler;
import com.cjburkey.playershops.Util;
import com.cjburkey.playershops.cmd.ICommand;
import com.cjburkey.playershops.cmd.ISubCommand;
import com.cjburkey.playershops.shop.PlayerShop;
import com.cjburkey.playershops.shop.ShopHandler;

public final class SubCommandAdd implements ISubCommand {
	
	public String getName() {
		return "add";
	}
	
	public String[] getArgs() {
		return new String[] { "buyPrice", "sellPrice", "player" };
	}
	
	public boolean playerOnly() {
		return true;
	}
	
	public int getRequiredArgs() {
		return 2;
	}
	
	public String getPermission() {
		return "playershops.edit";
	}
	
	public void onCall(ICommand parent, CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			Util.msg(false, sender, LanguageHandler.get("addIngameOnly"));
			return;
		}
		Player ply = (Player) sender;
		if (args.length == 3) {
			// TODO: ADMIN
			if (!ply.hasPermission("playershops.admin")) {
				Util.msg(false, ply, LanguageHandler.get("addMissingPermOther"));
				return;
			}
			return;
		}
		if (!ShopHandler.hasShop(ply.getUniqueId())) {
			Util.msg(true, ply, LanguageHandler.get("createShop"));
			return;
		}
		double buy = -2.0f;
		double sell = -2.0f;
		try {
			buy = (args[0].equalsIgnoreCase("disabled")) ? -1.0f : Double.parseDouble(args[0]);
			sell = (args[1].equalsIgnoreCase("disabled")) ? -1.0f : Double.parseDouble(args[1]);
			if (buy < 0.0d && buy != -2.0f) {
				buy = -1.0f;
			}
			if (sell < 0.0d && sell != -2.0f) {
				sell = -1.0f;
			}
		} catch(Exception e) {
		}
		if (buy < -1.0d || sell < -1.0d) {
			Util.msg(true, ply, LanguageHandler.get("addValidPrice"));
			return;
		}
		addToShop(ply.getUniqueId(), ply, buy, sell);
	}
	
	private void addToShop(UUID shopId, Player holder, double buy, double sell) {
		ItemStack stack = holder.getInventory().getItemInMainHand();
		if (stack == null || stack.getType() == null || stack.getType().equals(Material.AIR) || stack.getAmount() <= 0) {
			Util.msg(true, holder, LanguageHandler.get("addInHand"));
			return;
		}
		PlayerShop shop = ShopHandler.getShop(shopId);
		if (shop.hasItem(stack)) {
			Util.msg(true, holder, LanguageHandler.get("addAlreadyHas"));
			return;
		}
		shop.addItem(stack, buy, sell);
		ShopHandler.save();
		Util.msg(true, holder, LanguageHandler.get("addSuccess"));
	}
	
}