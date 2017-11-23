package com.cjburkey.playershops.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import com.cjburkey.playershops.PlayerShops;
import com.cjburkey.playershops.cmd.CommandBase;

public final class CommandShop extends CommandBase {

	public String getName() {
		return "shop";
	}

	public boolean playerOnly() {
		return true;
	}

	public String[] getArgs() {
		return new String[] { "player" };
	}

	public int getRequiredArgs() {
		return 1;
	}

	public Permission getPermission() {
		return PlayerShops.getInstance().getPermission("playershops.use");
	}

	public void onCall(CommandSender sender, String[] args) {
		
	}
	
}