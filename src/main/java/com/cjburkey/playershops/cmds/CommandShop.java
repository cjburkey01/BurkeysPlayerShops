package com.cjburkey.playershops.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import com.cjburkey.playershops.PlayerShops;
import com.cjburkey.playershops.cmd.CommandBase;
import com.cjburkey.playershops.cmd.SubCommandHandler;

public final class CommandShop extends CommandBase {
	
	private SubCommandHandler sch;
	
	public CommandShop() {
		sch = new SubCommandHandler(true);
		sch.addSubCommand(new SubCommandOpen());
		sch.addSubCommand(new SubCommandCreate());
		sch.addSubCommand(new SubCommandAdd());
		sch.addSubCommand(new SubCommandRemove());
		sch.addSubCommand(new SubCommandRefill());
	}
	
	public String getName() {
		return "shop";
	}
	
	public boolean playerOnly() {
		return true;
	}
	
	public Permission getPermission() {
		return PlayerShops.getInstance().getPermission("playershops.use");
	}
	
	public SubCommandHandler getSubCommandHandler() {
		return sch;
	}
	
	// -- IGNORED -- //
	
	public String[] getArgs() {
		return new String[] {  };
	}
	
	public int getRequiredArgs() {
		return 0;
	}
	
	public void onCall(CommandSender sender, String[] args) {
	}
	
}