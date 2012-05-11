package me.SHiLLySiT.LocationalCrafting;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class LocationalCrafting extends JavaPlugin {
	public PlayerListener listener;
	public Config config;
	public Commands commands;
	
	public void onEnable()
	{
		Log.initialize(this, Logger.getLogger("Minecraft"));
		listener = new PlayerListener(this);
		config = new Config(this);
		config.loadConfig();
		commands = new Commands(this);
		
	    Log.info("v" + this.getDescription().getVersion() + " is enabled!");
	}
	
	public void onDisable()
	{
		Log.info(" has been disabled!");
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		return commands.executeCommand(sender, cmd, commandLabel, args);
	}
	
}
