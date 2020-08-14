package me.chickenstyle.backpack.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import me.chickenstyle.backpack.FancyBags;
import me.chickenstyle.backpack.Message;

public class CraftEvent implements Listener{
	@EventHandler
	public void onCraft(CraftItemEvent e) {
		if (FancyBags.getVersionHandler().hasInventoryTag(e.getRecipe().getResult())) {
			if (e.getClickedInventory() == null) return;
				if (e.isShiftClick()) {
					e.setCancelled(true);
					e.getWhoClicked().sendMessage(Message.DISABLE_CRAFT.getMSG());
					return;
				}
			e.getInventory().setResult(FancyBags.getVersionHandler().addRandomTag(e.getRecipe().getResult()));
		}
	}
}
