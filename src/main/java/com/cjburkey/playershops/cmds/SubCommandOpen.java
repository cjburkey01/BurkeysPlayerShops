package com.cjburkey.playershops.cmds;

import org.bukkit.command.CommandSender;
import com.cjburkey.playershops.cmd.ICommand;
import com.cjburkey.playershops.cmd.ISubCommand;

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
		
	}
	
}