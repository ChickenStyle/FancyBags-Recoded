package me.chickenstyle.backpack;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface NMSHandler {
	public ItemStack addRandomTag(ItemStack item);
	public ItemStack addInventoryTag(ItemStack item,Inventory inv,int size,String title,int id);
	public boolean hasInventoryTag(ItemStack item);
	public String getInventoryTag(ItemStack item);
	public int getBackpackSize(ItemStack item);
	public String getBackPackTitle(ItemStack item);
	public int getBackpackID(ItemStack item);
}
