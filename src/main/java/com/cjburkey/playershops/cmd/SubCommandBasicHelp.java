package com.cjburkey.playershops.cmd;

import org.bukkit.command.CommandSender;
import com.cjburkey.playershops.Util;

public final class SubCommandBasicHelp implements ISubCommand {

	public String getName() {
		return "help";
	}

	public String[] getArgs() {
		return new String[] {  };
	}
	
	public boolean playerOnly() {
		return false;
	}

	public int getRequiredArgs() {
		return 0;
	}

	public String getPermission() {
		return null;
	}

	public void onCall(ICommand parent, CommandSender sender, String[] args) {
		StringBuilder out = new StringBuilder();
		out.append('\n');
		ISubCommand[] cmds = parent.getSubCommandHandler().getCommands();
		for (int i = 0; i < cmds.length; i ++) {
			out.append(' ');
			out.append(' ');
			out.append(SubCommandHandler.getUsage(false, parent, cmds[i]));
			if (i < cmds.length - 1) {
				out.append('\n');
			}
		}
		Util.msg(true, sender, out.toString());
	}
	
}