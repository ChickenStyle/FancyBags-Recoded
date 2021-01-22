package me.chickenstyle.backpack.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Container;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.sk89q.craftbook.mechanics.pipe.PipeSuckEvent;

import me.chickenstyle.backpack.FancyBags;

public class PipeEvents implements Listener{
	
	@EventHandler
	public void onPipeSuckEvent(PipeSuckEvent e) {
		
		List<ItemStack> items = e.getItems();
		if (!items.isEmpty()) {
			if (FancyBags.getVersionHandler().hasInventoryTag(items.get(0))) {
				e.setItems(new ArrayList<ItemStack>());
				
				Container block = (Container) e.getSuckedBlock().getState();
				
				
				block.getSnapshotInventory().addItem(items.get(0));
				block.update();
				
			}
		}
		

	}

}
