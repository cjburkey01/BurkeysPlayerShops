package com.cjburkey.playershops.cmds;

import org.bukkit.command.CommandSender;
import com.cjburkey.playershops.cmd.CommandBase;
import com.cjburkey.playershops.cmd.SubCommandHandler;

public final class CommandShop extends CommandBase {
	
	private SubCommandHandler sch;
	
	public CommandShop() {
		sch = new SubCommandHandler(true);
		sch.addSubCommand(new SubCommandOpen());
		sch.addSubCommand(new SubCommandCreate());
		sch.addSubCommand(new SubCommandRemove());
		sch.addSubCommand(new SubCommandAdd());
		sch.addSubCommand(new SubCommandRefill());
		sch.addSubCommand(new SubCommandDelete());
	}
	
	public String getName() {
		return "shop";
	}
	
	public boolean playerOnly() {
		return true;
	}
	
	public String getPermission() {
		return "playershops.use";
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