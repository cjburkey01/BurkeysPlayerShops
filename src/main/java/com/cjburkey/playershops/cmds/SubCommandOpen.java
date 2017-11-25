package com.cjburkey.playershops.cmds;

import java.util.UUID;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.cjburkey.playershops.PlayerStorage;
import com.cjburkey.playershops.Util;
import com.cjburkey.playershops.cmd.ICommand;
import com.cjburkey.playershops.cmd.ISubCommand;
import com.cjburkey.playershops.shop.ShopHandler;

public final class SubCommandOpen implements ISubCommand {
	
	public String getName() {
		return "open";
	}
	
	public String[] getArgs() {
		return new String[] { "player" };
	}
	
	public boolean playerOnly() {
		return true;
	}
	
	public int getRequiredArgs() {
		return 1;
	}
	
	public String getPermission() {
		return null;
	}
	
	public void onCall(ICommand parent, CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			Util.msg(sender, "&4Only ingame players may view shops.");
			return;
		}
		Player ply = (Player) sender;
		UUID other = PlayerStorage.getUUID(args[0]);
		if (other == null) {
			Util.msg(sender, "&4That player has not joined the server.");
			return;
		}
		if (!ShopHandler.hasShop(other)) {
			Util.msg(ply, "&4That user does not have a shop.");
			return;
		}
		ShopHandler.showShop(other, ply, 0);
		Util.msg(ply, "&2The shop has been opened.");
	}
	
}