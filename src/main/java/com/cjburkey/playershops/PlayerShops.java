package com.cjburkey.playershops;

import java.util.List;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import com.cjburkey.playershops.cmd.CommandHandler;
import com.cjburkey.playershops.cmds.CommandShop;

public class PlayerShops extends JavaPlugin {
	
	private static PlayerShops instance;
	
	public void onEnable() {
		instance = this;
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		if (!EconHandler.init(this)) {
			Util.log("Vault support could not be initialized.");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		Util.log("Vault support initialized.");
		
		CommandHandler.registerCommand(this, new CommandShop());
		Util.log("Registered commands.");
	}
	
	public void onDisable() {
		
	}
	
	public List<Permission> getPermissions() {
		return getDescription().getPermissions();
	}
	
	public Permission getPermission(String name) {
		for (Permission p : getPermissions()) {
			if (p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}
	
	public static PlayerShops getInstance() {
		return instance;
	}
	
}