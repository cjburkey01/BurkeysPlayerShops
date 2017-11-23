package com.cjburkey.playershops.cmd;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public interface ICommand {
	
	String getName();
	boolean playerOnly();
	String[] getArgs();
	int getRequiredArgs();
	Permission getPermission();
	void onCall(CommandSender sender, String[] args);
	
}