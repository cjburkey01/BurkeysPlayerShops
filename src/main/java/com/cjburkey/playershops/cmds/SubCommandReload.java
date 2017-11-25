package com.cjburkey.playershops.cmds;

import org.bukkit.command.CommandSender;
import com.cjburkey.playershops.LanguageHandler;
import com.cjburkey.playershops.PlayerShops;
import com.cjburkey.playershops.Util;
import com.cjburkey.playershops.cmd.ICommand;
import com.cjburkey.playershops.cmd.ISubCommand;

public final class SubCommandReload implements ISubCommand {
	
	public String getName() {
		return "reload";
	}
	
	public String[] getArgs() {
		return new String[] {  };
	}
	
	public boolean playerOnly() {
		return false;
	}
	
	public int getRequiredArgs() {
		return 0;
	}
	
	public String getPermission() {
		return "playershops.admin";
	}
	
	public void onCall(ICommand parent, CommandSender sender, String[] args) {
		Util.log("Disabling plugin...");
		PlayerShops.getInstance().onDisable();
		Util.log("Enabling plugin...");
		PlayerShops.getInstance().onEnable();
		Util.log("Reloaded plugin.");
		Util.msg(true, sender, LanguageHandler.get("reloaded"));
	}
	
}