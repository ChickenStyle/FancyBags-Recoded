package me.chickenstyle.backpack.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import me.chickenstyle.backpack.FancyBags;
import me.chickenstyle.backpack.customevents.BackpackCloseEvent;
import me.chickenstyle.backpack.customholders.BackpackHolder;
import me.chickenstyle.backpack.customholders.CreateRecipeHolder;
import me.chickenstyle.backpack.customholders.RejectItemsHolder;
import me.chickenstyle.backpack.utilsfolder.Utils;

public class CloseInventoryEvent implements Listener{
	@SuppressWarnings({ "deprecation" })
	@EventHandler
	public void onCloseInventory(InventoryCloseEvent e) {
		Player player = (Player) e.getPlayer();
		if (e.getInventory().getHolder() instanceof BackpackHolder) {
			if (!RightClickEvent.duped.containsKey(e.getPlayer().getUniqueId())) {
				ItemStack backpack = Utils.loadBackpack(player);
				e.getPlayer().setItemInHand(backpack);
				player.playSound(player.getLocation(), Utils.getVersionChestCloseSound(), (float) FancyBags.getInstance().getConfig().getDouble("soundLevelOfBackpacks"), (float) FancyBags.getInstance().getConfig().getDouble("pitchLevelOfBackpacks"));
				Bukkit.getPluginManager().callEvent(new BackpackCloseEvent(player, e.getView().getTopInventory()));				
			} else {
				RightClickEvent.duped.remove(e.getPlayer().getUniqueId());
			}
		}
		
		if (e.getInventory().getHolder() instanceof RejectItemsHolder) {
			if (FancyBags.creatingBackpack.get(player.getUniqueId()).getReject().getItems() == null) {
				player.sendMessage(ChatColor.RED + "Backpack creation has been disbanded!");
				FancyBags.creatingBackpack.remove(player.getUniqueId());
			}
		}
		
		if (e.getInventory().getHolder() instanceof CreateRecipeHolder) {
			if (FancyBags.creatingBackpack.containsKey(player.getUniqueId())) {
				player.sendMessage(ChatColor.RED + "Backpack creation has been disbanded!");
				FancyBags.creatingBackpack.remove(player.getUniqueId());	
			}
		}
	}
	
	

}
