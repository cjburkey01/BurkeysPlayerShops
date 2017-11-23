package com.cjburkey.playershops.cmd;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import com.cjburkey.playershops.Util;

public final class SubCommandHelp implements ISubCommand {

	public String getName() {
		return "help";
	}

	public String[] getArgs() {
		return new String[] {  };
	}

	public int getRequiredArgs() {
		return 0;
	}

	public Permission getPermission() {
		return null;
	}

	public void onCall(ICommand parent, CommandSender sender, String[] args) {
		StringBuilder out = new StringBuilder();
		out.append("&4Usage: /");
		out.append(parent.getName());
		out.append(' ');
		out.append('<');
		ISubCommand[] cmds = parent.getSubCommandHandler().getCommands();
		for (int i = 0; i < cmds.length; i ++) {
			out.append(cmds[i].getName());
			if (i < cmds.length - 1) {
				out.append('/');
			}
		}
		out.append('>');
		Util.msg(sender, out.toString());
	}
	
}