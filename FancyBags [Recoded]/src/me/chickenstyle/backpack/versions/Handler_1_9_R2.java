package me.chickenstyle.backpack.versions;

import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.chickenstyle.backpack.NMSHandler;
import me.chickenstyle.backpack.utilsfolder.RandomString;
import me.chickenstyle.backpack.utilsfolder.Utils;
import net.minecraft.server.v1_9_R2.NBTTagCompound;

public class Handler_1_9_R2 implements NMSHandler{
	
	@Override
	public ItemStack addRandomTag(ItemStack item) {
		net.minecraft.server.v1_9_R2.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		String random = new RandomString(10).nextString();
		itemCompound.setString(random, random);
		nmsItem.setTag(itemCompound);
		return CraftItemStack.asBukkitCopy(nmsItem);
	}
	
	@Override
	public ItemStack addInventoryTag(ItemStack item, Inventory inv,int size,String title,int id) {
		net.minecraft.server.v1_9_R2.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		itemCompound.setString("BackPack", Utils.inventoryToBase64(inv));
		itemCompound.setInt("Size", size);
		itemCompound.setString("Title", title);
		itemCompound.setInt("BackpackID", id);
		nmsItem.setTag(itemCompound);
		return CraftItemStack.asBukkitCopy(nmsItem);
	}
	
	@Override
	public boolean hasInventoryTag(ItemStack item) {
		net.minecraft.server.v1_9_R2.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		if (itemCompound.hasKey("BackPack")) {
			return true;
		}
		return false;
	}

	@Override
	public String getInventoryTag(ItemStack item) {
		net.minecraft.server.v1_9_R2.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		return itemCompound.getString("BackPack");
	}
	
	
	@Override
	public int getBackpackSize(ItemStack item) {
		net.minecraft.server.v1_9_R2.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		return itemCompound.getInt("Size");
	}

	@Override
	public String getBackPackTitle(ItemStack item) {
		net.minecraft.server.v1_9_R2.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		return itemCompound.getString("Title");
	}
	
	@Override
	public int getBackpackID(ItemStack item) {
		net.minecraft.server.v1_9_R2.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		return itemCompound.getInt("BackpackID");
	}
}
