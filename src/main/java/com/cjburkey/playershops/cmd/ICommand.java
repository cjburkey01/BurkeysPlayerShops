package com.cjburkey.playershops.cmd;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public interface ICommand {
	
	String getName();
	boolean playerOnly();
	Permission getPermission();
	SubCommandHandler getSubCommandHandler();			// Or null if none.
	String[] getArgs();									// Ignored if subcommand handler is not null.
	int getRequiredArgs();								// Ignored if subcommand handler is not null.
	void onCall(CommandSender sender, String[] args);	// Ignored if subcommand handler is not null.
	
}