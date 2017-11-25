package com.cjburkey.playershops.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.cjburkey.playershops.LanguageHandler;
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
			Util.msg(false, sender, LanguageHandler.get("deleteIngameOnly"));
			return;
		}
		Player ply = (Player) sender;
		if (args.length == 1) {
			// TODO: ADMIN
			if (!ply.hasPermission("playershops.admin")) {
				Util.msg(false, sender, LanguageHandler.get("deleteMissingPermOther"));
				return;
			}
			return;
		}
		if (!ShopHandler.hasShop(ply.getUniqueId())) {
			Util.msg(true, sender, LanguageHandler.get("deleteNoShop"));
			return;
		}
		ShopHandler.deleteShop(ply.getUniqueId());
		ShopHandler.save();
		Util.msg(true, sender, LanguageHandler.get("deleteSuccess"));
	}
	
}