package com.cjburkey.playershops.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.cjburkey.playershops.Util;
import com.cjburkey.playershops.cmd.ICommand;
import com.cjburkey.playershops.cmd.ISubCommand;
import com.cjburkey.playershops.shop.ShopHandler;

public final class SubCommandDelete implements ISubCommand {
	
	public String getName() {
		return "delete";
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
			Util.msg(sender, "&4Only ingame players may create shops for themselves.");
			return;
		}
		Player ply = (Player) sender;
		if (args.length == 1) {
			if (!ply.hasPermission("playershops.admin")) {
				Util.msg(sender, "&4You do not have permission to delete shops for other players.");
				return;
			}
			return;
		}
		if (!ShopHandler.hasShop(ply.getUniqueId())) {
			Util.msg(sender, "&4You do not have a shop. Use &l/shop create&r&4 to create one.");
			return;
		}
		ShopHandler.deleteShop(ply.getUniqueId());
		Util.msg(sender, "&2Your shop has been deleted.");
	}
	
}