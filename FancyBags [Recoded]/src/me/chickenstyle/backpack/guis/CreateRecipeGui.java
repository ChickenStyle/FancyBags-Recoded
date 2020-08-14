package me.chickenstyle.backpack.guis;


import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.chickenstyle.backpack.customholders.CreateRecipeHolder;
import me.chickenstyle.backpack.utilsfolder.Utils;


public class CreateRecipeGui {
	public CreateRecipeGui(Player player) {
		ArrayList<Integer> emptySlots = new ArrayList<Integer>();
		emptySlots.add(12);
		emptySlots.add(13);
		emptySlots.add(14);
		emptySlots.add(21);
		emptySlots.add(22);
		emptySlots.add(23);
		emptySlots.add(30);
		emptySlots.add(31);
		emptySlots.add(32);
		
		Inventory gui = Bukkit.createInventory(new CreateRecipeHolder(), 45, Utils.color("&7&lAdd recipe for the backpack!"));
		for (int i = 0; i < 45;i++) {
			if (!emptySlots.contains(i)) {
				gui.setItem(i, Utils.getGrayVersionGlass());
			}
			
			if (i == 44) {
				gui.setItem(i, Utils.getGreenVersionGlass());
			}
		}
		player.openInventory(gui);
	}
}
