package com.cjburkey.playershops.cmd;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public interface ISubCommand {
	
	String getName();
	String[] getArgs();
	int getRequiredArgs();
	Permission getPermission();
	void onCall(ICommand parent, CommandSender sender, String[] args);
	
}