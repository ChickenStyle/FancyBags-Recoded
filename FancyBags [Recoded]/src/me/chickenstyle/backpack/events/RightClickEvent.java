package me.chickenstyle.backpack.events;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import me.chickenstyle.backpack.FancyBags;
import me.chickenstyle.backpack.customevents.BackpackOpenEvent;
import me.chickenstyle.backpack.utilsfolder.Utils;


public class RightClickEvent implements Listener{
	public static HashMap<UUID,Boolean> duped = new HashMap<UUID,Boolean>(); 
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onRightClick(PlayerInteractEvent e) throws IOException {
		if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (e.getItem() == null || e.getItem().getType() == Material.AIR) return;
			if (!Bukkit.getVersion().contains("1.8")) {
				if (FancyBags.getVersionHandler().hasInventoryTag(e.getPlayer().getInventory().getItemInOffHand())) {e.setCancelled(true); return;}
			}
			
			
			if (FancyBags.getVersionHandler().hasInventoryTag(e.getItem())) {
				Player player = e.getPlayer();
				e.setCancelled(true);
				player.playSound(player.getLocation(), Utils.getVersionChestSound(), 1f, 1f);
				
				Inventory data = Utils.inventoryFromBase64(FancyBags.getVersionHandler().getInventoryTag(e.getItem()));
				Inventory gui = Bukkit.createInventory(data.getHolder(), data.getSize(),
				Utils.color(FancyBags.getVersionHandler().getBackPackTitle(e.getItem())));
				gui.setContents(data.getContents());
				
				BackpackOpenEvent event = new BackpackOpenEvent(player, gui);
				Bukkit.getPluginManager().callEvent(event);
				
				if (!event.isCancelled()) {
					int slotsAmount = FancyBags.getVersionHandler().getBackpackSize(e.getItem());
					for (int i = slotsAmount ;i < gui.getSize() ;i++) {
						gui.setItem(i, Utils.getRedVersionGlass());
					}
					player.openInventory(gui);
					
					int slot = e.getPlayer().getInventory().getHeldItemSlot();
					Bukkit.getScheduler().scheduleSyncDelayedTask(FancyBags.getInstance(), () -> {
						if (player.getItemInHand() != null && !player.getItemInHand().getType().equals(Material.AIR)) {
							if (player.getInventory().getHeldItemSlot() != slot) {
									duped.put(player.getUniqueId(), true);
									player.closeInventory();
								}
						} else {
							duped.put(player.getUniqueId(), true);
							player.closeInventory();
						}

					},5);
				}
			}
		}
	}
}
