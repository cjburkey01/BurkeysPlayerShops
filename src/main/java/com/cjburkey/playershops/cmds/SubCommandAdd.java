package com.cjburkey.playershops.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import com.cjburkey.playershops.cmd.ICommand;
import com.cjburkey.playershops.cmd.ISubCommand;

public class SubCommandAdd implements ISubCommand {
	
	public String getName() {
		return "add";
	}
	
	public String[] getArgs() {
		return new String[] { "player" };
	}
	
	public int getRequiredArgs() {
		return 0;
	}
	
	public Permission getPermission() {
		return null;
	}
	
	public void onCall(ICommand parent, CommandSender sender, String[] args) {
		
	}
	
}