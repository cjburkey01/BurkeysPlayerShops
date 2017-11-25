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

public final class SubCommandRefill implements ISubCommand {
	
	public String getName() {
		return "refill";
	}
	
	public String[] getArgs() {
		return new String[] { "player" };
	}
	
	public boolean playerOnly() {
		return true;
	}
	
	public int getRequiredArgs() {
		return 0;
	}
	
	public String getPermission() {
		return "playershops.edit";
	}
	
	public void onCall(ICommand parent, CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			Util.msg(false, sender, LanguageHandler.get("refillIngameOnly"));
			return;
		}
		Player ply = (Player) sender;
		if (args.length == 3) {
			// TODO: ADMIN
			if (!ply.hasPermission("playershops.admin")) {
				Util.msg(false, ply, LanguageHandler.get("refillMissingPermOther"));
				return;
			}
			return;
		}
		if (!ShopHandler.hasShop(ply.getUniqueId())) {
			Util.msg(true, ply, LanguageHandler.get("createShop"));
			return;
		}
		refillShop(ply.getUniqueId(), ply);
	}
	
	private void refillShop(UUID shopId, Player holder) {
		ItemStack stack = holder.getInventory().getItemInMainHand();
		if (stack == null || stack.getType() == null || stack.getType().equals(Material.AIR) || stack.getAmount() <= 0) {
			Util.msg(true, holder, LanguageHandler.get("refillInHand"));
			return;
		}
		PlayerShop shop = ShopHandler.getShop(shopId);
		if (!shop.hasItem(stack)) {
			Util.msg(true, holder, LanguageHandler.get("refillItemNotInShop"));
			return;
		}
		shop.addQuantity(stack);
		ShopHandler.save();
		Util.msg(true, holder, LanguageHandler.get("refillSuccess"));
	}
	
}