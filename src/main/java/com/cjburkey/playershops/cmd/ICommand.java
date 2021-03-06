package com.cjburkey.playershops.cmd;

import org.bukkit.command.CommandSender;

public interface ICommand {
	
	String getName();
	boolean playerOnly();
	String getPermission();
	SubCommandHandler getSubCommandHandler();			// Or null if none.
	String[] getArgs();									// Ignored if subcommand handler is not null.
	int getRequiredArgs();								// Ignored if subcommand handler is not null.
	void onCall(CommandSender sender, String[] args);	// Ignored if subcommand handler is not null.
	
}