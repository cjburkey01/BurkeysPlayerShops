package com.cjburkey.playershops.cmds;

import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
			Util.msg(sender, "&4Only ingame players may create shops for themselves.");
			return;
		}
		Player ply = (Player) sender;
		if (args.length == 3) {
			// TODO: ADMIN
			if (!ply.hasPermission("playershops.admin")) {
				Util.msg(ply, "&4You do not have permission to delete shops for other players.");
				return;
			}
			return;
		}
		if (!ShopHandler.hasShop(ply.getUniqueId())) {
			Util.msg(ply, "&4You do not have a shop. Use &l/shop create&r&4 to create one.");
			return;
		}
		double buy = -1.0f;
		double sell = -1.0f;
		try {
			buy = Double.parseDouble(args[0]);
			sell = Double.parseDouble(args[1]);
		} catch(Exception e) {
		}
		if (buy < 0.0d || sell < 0.0d) {
			Util.msg(ply, "&4Please enter valid prices");
			return;
		}
		addToShop(ply.getUniqueId(), ply, buy, sell);
	}
	
	private void addToShop(UUID shopId, Player holder, double buy, double sell) {
		ItemStack stack = holder.getInventory().getItemInMainHand();
		if (stack == null || stack.getType() == null || stack.getType().equals(Material.AIR) || stack.getAmount() <= 0) {
			Util.msg(holder, "&4Please hold the item to add to your shop in your main hand.");
			return;
		}
		PlayerShop shop = ShopHandler.getShop(shopId);
		if (shop.hasItem(stack)) {
			Util.msg(holder, "&4Your shop already contains this item.");
			return;
		}
		shop.addItem(stack, buy, sell);
		ShopHandler.save();
		Util.msg(holder, "&2The item has been added to your shop. Use &l/shop refill&r&2 to add items to the amount.");
	}
	
}