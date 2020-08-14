package me.chickenstyle.backpack.guis;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.chickenstyle.backpack.customholders.RejectItemsHolder;
import me.chickenstyle.backpack.utilsfolder.Utils;


public class RejectItemsGui {
	public RejectItemsGui(Player player) {
		Inventory gui = Bukkit.createInventory(new RejectItemsHolder(), 54, Utils.color("&7&lPut the items here!"));
		ItemStack green = Utils.getGreenVersionGlass();
		ItemMeta meta = green.getItemMeta();
		meta.setDisplayName(Utils.color("&aClick here to save the blacklist/whitelist!"));
		green.setItemMeta(meta);
		gui.setItem(53, green);
		
		player.openInventory(gui);
	}
}
