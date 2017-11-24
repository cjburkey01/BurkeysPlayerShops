package com.cjburkey.playershops.cmd;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.CommandSender;
import com.cjburkey.playershops.Util;

public final class SubCommandHandler {
	
	private List<ISubCommand> cmds = new ArrayList<>();
	
	public SubCommandHandler(boolean autoHelp) {
		if (autoHelp) {
			addSubCommand(new SubCommandBasicHelp());
		}
	}
	
	public void onCall(ICommand parent, String subCmd, CommandSender sender, String[] args) {
		ISubCommand cmd = getCommand(subCmd);
		if (cmd == null) {
			Util.msg(sender, "&4Usage: /" + parent.getName() + " help");
			return;
		}
		if (cmd.getPermission() != null && !sender.hasPermission(cmd.getPermission())) {
			Util.msg(sender, "&4You do not have permission to execute /" + parent.getName() + ' ' + subCmd);
			return;
		}
		if (args.length < cmd.getRequiredArgs() || args.length > cmd.getArgs().length) {
			showUsage(parent, cmd, sender);
			return;
		}
		cmd.onCall(parent, sender, args);
	}
	
	public void addSubCommand(ISubCommand cmd) {
		if (getCommand(cmd.getName()) == null) {
			cmds.add(cmd);
		}
	}
	
	public void removeSubCommand(ISubCommand cmd) {
		cmds.remove(cmd);
	}
	
	public ISubCommand[] getCommands() {
		return cmds.toArray(new ISubCommand[cmds.size()]);
	}
	
	private ISubCommand getCommand(String name) {
		for (ISubCommand command : cmds) {
			if (command.getName().equalsIgnoreCase(name)) {
				return command;
			}
		}
		return null;
	}
	
	private static void showUsage(ICommand cmd, ISubCommand subCmd, CommandSender sender) {
		StringBuilder out = new StringBuilder();
		out.append("&4Usage: /");
		out.append(cmd.getName());
		out.append(' ');
		out.append(subCmd.getName());
		out.append(' ');
		for (int i = 0; i < subCmd.getArgs().length; i ++) {
			boolean required = (i < subCmd.getRequiredArgs());
			out.append(required ? '<' : '[');
			out.append(subCmd.getArgs()[i]);
			out.append(required ? '>' : ']');
			if (i < subCmd.getArgs().length - 1) {
				out.append(' ');
			}
		}
		Util.msg(sender, out.toString());
	}
	
}