package com.cjburkey.playershops.cmds;

import java.util.UUID;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.cjburkey.playershops.LanguageHandler;
import com.cjburkey.playershops.PlayerStorage;
import com.cjburkey.playershops.Util;
import com.cjburkey.playershops.cmd.ICommand;
import com.cjburkey.playershops.cmd.ISubCommand;
import com.cjburkey.playershops.shop.ShopHandler;

public final class SubCommandOpen implements ISubCommand {
	
	public String getName() {
		return "open";
	}
	
	public String[] getArgs() {
		return new String[] { "player" };
	}
	
	public boolean playerOnly() {
		return true;
	}
	
	public int getRequiredArgs() {
		return 1;
	}
	
	public String getPermission() {
		return null;
	}
	
	public void onCall(ICommand parent, CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			Util.msg(false, sender, LanguageHandler.get("openIngameOnly"));
			return;
		}
		Player ply = (Player) sender;
		UUID other = PlayerStorage.getUUID(args[0]);
		if (other == null) {
			Util.msg(false, sender, LanguageHandler.get("openPlayerNotJoined"));
			return;
		}
		if (!ShopHandler.hasShop(other)) {
			Util.msg(true, ply, LanguageHandler.get("openShopNotCreated"));
			return;
		}
		ShopHandler.showShop(other, ply, 0);
		Util.msg(true, ply, LanguageHandler.getFormat("openSuccess", PlayerStorage.getName(other)));
	}
	
}