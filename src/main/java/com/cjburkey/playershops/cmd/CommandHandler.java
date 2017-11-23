package com.cjburkey.playershops.cmd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import com.cjburkey.playershops.Util;

public final class CommandHandler {
	
	private static final List<ICommand> cmds = new ArrayList<>();
	
	public static void onCommand(String command, CommandSender sender, String[] args) {
		ICommand cmd = getCommand(command);
		if (cmd == null) {
			Util.msg(sender, "&4Command " + command + " called but does not exist in the command system.");
			return;
		}
		if (cmd.playerOnly() && !(sender instanceof Player)) {
			Util.msg(sender, "&4Only ingame players may execute /" + cmd.getName());
			return;
		}
		if (cmd.getPermission() != null && !sender.hasPermission(cmd.getPermission())) {
			Util.msg(sender, "&4You do not have permission to execute /" + cmd.getName());
			return;
		}
		if (cmd.getSubCommandHandler() == null) {
			if (args.length < cmd.getRequiredArgs() || args.length > cmd.getArgs().length) {
				showUsage(cmd, sender);
				return;
			}
			cmd.onCall(sender, args);
		} else {
			if (args.length == 0) {
				Util.msg(sender, "&4Usage: /" + cmd.getName() + " help");
				return;
			}
			cmd.getSubCommandHandler().onCall(cmd, args[0], sender, Arrays.copyOfRange(args, 1, args.length));
		}
	}
	
	private static ICommand getCommand(String name) {
		for (ICommand command : cmds) {
			if (command.getName().equalsIgnoreCase(name)) {
				return command;
			}
		}
		return null;
	}
	
	private static void showUsage(ICommand cmd, CommandSender sender) {
		StringBuilder out = new StringBuilder();
		out.append("&4Usage: /");
		out.append(cmd.getName());
		out.append(' ');
		for (int i = 0; i < cmd.getArgs().length; i ++) {
			boolean required = (i < cmd.getRequiredArgs());
			out.append(required ? '<' : '[');
			out.append(cmd.getArgs()[i]);
			out.append(required ? '>' : ']');
			if (i < cmd.getArgs().length - 1) {
				out.append(' ');
			}
		}
		Util.msg(sender, out.toString());
	}
	
	public static boolean registerCommand(JavaPlugin plugin, CommandBase cmd) {
		plugin.getCommand(cmd.getName()).setExecutor(cmd);
		return cmds.add(cmd);
	}
	
	public static boolean removeCommand(ICommand cmd) {
		return cmds.remove(cmd);
	}
	
}