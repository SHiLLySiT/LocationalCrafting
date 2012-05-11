package me.SHiLLySiT.LocationalCrafting;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands {
	private LocationalCrafting plugin;
	
	public Commands(LocationalCrafting instance)
    {
    	plugin = instance;
    }
	
	public boolean executeCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (command.getName().equalsIgnoreCase("locationalcrafting")) {
			if (args.length == 0) { 
				// no arguments
			} else { 
				if (args.length == 1 && args[0].equalsIgnoreCase("reload")) { // reloads config
					if (sender instanceof Player) { 
						Player p = (Player) sender;
						if (p.hasPermission("locationalcrafting.reload")) {
							plugin.reloadConfig();
							plugin.config.loadConfig();
							p.sendMessage("Reloaded LocationalCrafting!");
						} else {
							p.sendMessage(ChatColor.RED + "You don't have permission for that!");
						}
					} else {
						plugin.reloadConfig();
						plugin.config.loadConfig();
						Log.info("Reloaded LocationalCrafting");
					}
				}
			}
			return true;
		}
		return false;
	}
}
