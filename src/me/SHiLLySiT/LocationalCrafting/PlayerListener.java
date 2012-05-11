package me.SHiLLySiT.LocationalCrafting;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {
	private LocationalCrafting plugin;
	
    public PlayerListener(LocationalCrafting instance)
    {
    	plugin = instance;
        plugin.getServer().getPluginManager().registerEvents(this, instance);
    }
    
    public void cancelSmelt(Player player, InventoryClickEvent event, String group)
    {
    	if (plugin.config.showDenyMessage(group)) {
    		player.sendMessage(plugin.config.getColor() + plugin.config.getDenyMessage(group));
    	}
		event.setCancelled(true);
    }
    
    public void cancelCraft(Player player, PrepareItemCraftEvent event, String group)
    {
    	if (plugin.config.showDenyMessage(group)) {
    		player.sendMessage(plugin.config.getColor() + plugin.config.getDenyMessage(group));
    	}
		event.getInventory().setItem(0, new ItemStack(0, 0));
    }
    
    @EventHandler 
    public void OnPlayerClick(PlayerInteractEvent event)
    {
    	if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Block block = event.getClickedBlock();
            Player player = event.getPlayer();
            if (block.getType().equals(Material.WORKBENCH) || block.getType().equals(Material.FURNACE)) { // if player clicked a workbench or furnace
            	plugin.config.setBlock(player, block);
            }
    	}
    }
    
    @EventHandler
    public void onClick(InventoryClickEvent event)
    {
    	if (event.getInventory().getName().equals("container.furnace") && event.getSlot() == 0 && event.getCursor().getType() != Material.AIR) {
    		
    		List<HumanEntity> vl = event.getViewers();
	        Player player = null;
	        for(Iterator<HumanEntity> iterator = vl.iterator(); iterator.hasNext();)
	        {
	            HumanEntity he = (HumanEntity)iterator.next();
	            player = (Player)he;
	        }
	        
        	Block block = plugin.config.getBlock(player);
        	Block blockAbove = block.getWorld().getBlockAt(block.getX(), block.getY() + 1, block.getZ());
        	ItemStack input = event.getCursor();
        	
        	if (blockAbove.getType().equals(Material.WALL_SIGN)) { // if block above is a wall sign
        		
        		Sign sign = (Sign) blockAbove.getState();
        		String group = sign.getLine(0);
        		
        		if (plugin.config.getCraftGroup(group) != null) { // if above is wall sign
    	    		if (plugin.config.getCraftGroup(group).contains(input.getType().name())) {
    	    			//can smelt
    	    		} else {
    	    			cancelSmelt(player, event, group);
    	    		}
        		} else { // if no group
        			if (plugin.config.useGlobalGroup() && plugin.config.getCraftGroup("global").contains(input.getType().name())) {
        				// can craft
        			} else {
        				cancelSmelt(player, event, "global");
        			}
        		}
            } else { // if no sign
            	if (plugin.config.useGlobalGroup() && plugin.config.getCraftGroup("global").contains(input.getType().name())) {
    				// can craft
    			} else {
    				cancelSmelt(player, event, "global");
    			}
            }
    	}
    }
    
    @EventHandler
    public void onCraftItem(PrepareItemCraftEvent event)
    {
    	if (event.getView().getType().name().equals("WORKBENCH")) {
	    	
	        List<HumanEntity> vl = event.getViewers();
	        Player player = null;
	        for(Iterator<HumanEntity> iterator = vl.iterator(); iterator.hasNext();)
	        {
	            HumanEntity he = (HumanEntity)iterator.next();
	            player = (Player)he;
	        }
	        
	        Block block = plugin.config.getBlock(player);
	    	Block blockAbove = player.getWorld().getBlockAt(block.getX(), block.getY() + 1, block.getZ());
	    	ItemStack output = event.getRecipe().getResult();
	    	
	    	if (blockAbove.getType().equals(Material.WALL_SIGN)) { // if block above is a wall sign
	    		Sign sign = (Sign) blockAbove.getState();
	    		String group = sign.getLine(0);
	    		
	    		if (plugin.config.getCraftGroup(group) != null) { // if has crafting group
		    		if (plugin.config.getCraftGroup(group).contains(output.getData().getItemType().name())) {
		    			//can craft
		    		} else {
		    			cancelCraft(player, event, group);
		    		}
	    		} else { // if has no group
	    			if (plugin.config.useGlobalGroup() && plugin.config.getCraftGroup("global").contains(output.getData().getItemType().name())) {
	    				// can craft
	    			} else {
	    				cancelCraft(player, event, "global");
	    			}
	    		}
	        } else { // if not sign
	        	if (plugin.config.useGlobalGroup() && plugin.config.getCraftGroup("global").contains(output.getData().getItemType().name())) {
    				// can craft
    			} else {
    				cancelCraft(player, event, "global");
    			}
	        }
    	}
    }
    
}
