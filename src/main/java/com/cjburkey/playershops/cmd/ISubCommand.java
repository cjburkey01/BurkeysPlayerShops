package com.cjburkey.playershops.cmd;

import org.bukkit.command.CommandSender;

public interface ISubCommand {
	
	String getName();
	String[] getArgs();
	boolean playerOnly();		// Ignored if parent is player-only.
	int getRequiredArgs();
	String getPermission();
	void onCall(ICommand parent, CommandSender sender, String[] args);
	
}