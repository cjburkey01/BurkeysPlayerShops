package com.cjburkey.playershops.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import com.cjburkey.playershops.cmd.ICommand;
import com.cjburkey.playershops.cmd.ISubCommand;

public class SubCommandOpen implements ISubCommand {
	
	public String getName() {
		return "open";
	}
	
	public String[] getArgs() {
		return new String[] { "player" };
	}
	
	public int getRequiredArgs() {
		return 1;
	}
	
	public Permission getPermission() {
		return null;
	}
	
	public void onCall(ICommand parent, CommandSender sender, String[] args) {
		
	}
	
}