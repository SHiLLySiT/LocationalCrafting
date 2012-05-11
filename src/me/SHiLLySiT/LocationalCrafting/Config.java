package me.SHiLLySiT.LocationalCrafting;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Config {
	private LocationalCrafting plugin;
	private HashMap<Player, Block> blockStore = new HashMap<Player, Block>();
    private HashMap<String, List<String>> craftGroups = new HashMap<String, List<String>>();
	
    public Config(LocationalCrafting instance)
    {
    	plugin = instance;
    	loadDefaults();
    }
    
    public void setBlock(Player player, Block block)
    {
    	blockStore.put(player, block);
    }
    
    public Block getBlock(Player player)
    {
    	return blockStore.get(player);
    }
    
    public List<String> getCraftGroup(String group)
    {
    	return craftGroups.get(group);
    }
    
    public boolean showDenyMessage(String group)
    {
    	return plugin.getConfig().getBoolean("groups." + group + ".showDenyMessage");
    }
    
    public String getDenyMessage(String group)
    {
    	String str = plugin.getConfig().getString("groups." + group + ".denyMessage");
    	return (str != null) ? str : "You can't craft that here!";
    }
    
    public ChatColor getColor()
    {
    	String str = plugin.getConfig().getString("color");
    	return (str != null) ? ChatColor.valueOf(str.toUpperCase()) : ChatColor.RED;
    }
    
    public boolean useGlobalGroup()
    {
    	return plugin.getConfig().getBoolean("useGlobalGroup");
    }
    
    public void loadDefaults()
    {
		plugin.getConfig().options().copyDefaults(true);
    	
		plugin.getConfig().addDefault("debug", false);
		plugin.getConfig().addDefault("color", "RED");
		plugin.getConfig().addDefault("useGlobalGroup", false);
		String[] global = { "SIGN" };
		plugin.getConfig().addDefault("groups.global.items", global);
		plugin.getConfig().addDefault("groups.global.showDenyMessage", true);
		plugin.getConfig().addDefault("groups.global.denyMessage", "You can't craft that here!");
		String[] bakery = { "BREAD", "CAKE", "MUSHROOM_SOUP" };
		plugin.getConfig().addDefault("groups.bakery.items", bakery);
		plugin.getConfig().addDefault("groups.bakery.showDenyMessage", true);
		plugin.getConfig().addDefault("groups.bakery.denyMessage", "You can't craft that at the bakery!");
		
		plugin.saveConfig();
    }
    
    public void loadConfig()
	{
    	craftGroups.clear();
		Set<String> groups = plugin.getConfig().getConfigurationSection("groups").getKeys(false);
		Log.debug("groups:" + groups);
		Iterator<String> itr = groups.iterator();
		
		while (itr.hasNext()) {
			String name = itr.next();
			List<String> items = plugin.getConfig().getStringList("groups." + name + ".items");
			Log.debug(name + ":" + items);
			craftGroups.put(name, items);
        }
	}
}
