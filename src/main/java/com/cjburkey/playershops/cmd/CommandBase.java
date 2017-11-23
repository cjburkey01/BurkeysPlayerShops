package com.cjburkey.playershops.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class CommandBase implements CommandExecutor, ICommand {
	
	public boolean onCommand(CommandSender ply, Command cmd, String label, String[] args) {
		CommandHandler.onCommand(cmd.getName(), ply, args);
		return true;
	}
	
}