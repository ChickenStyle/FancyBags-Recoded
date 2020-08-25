package me.chickenstyle.backpack.events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.chickenstyle.backpack.FancyBags;
import me.chickenstyle.backpack.customevents.BackpackCloseEvent;
import me.chickenstyle.backpack.customholders.BackpackHolder;
import me.chickenstyle.backpack.customholders.CreateRecipeHolder;
import me.chickenstyle.backpack.customholders.RejectItemsHolder;
import me.chickenstyle.backpack.utilsfolder.Utils;

public class CloseInventoryEvent implements Listener{
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onCloseInventory(InventoryCloseEvent e) {
		Player player = (Player) e.getPlayer();

		
		
		
		if (e.getInventory().getHolder() instanceof BackpackHolder) {
			if (!RightClickEvent.duped.containsKey(e.getPlayer().getUniqueId())) {
				ItemStack backpack = FancyBags.getVersionHandler().addInventoryTag(
						e.getPlayer().getItemInHand()
						, e.getView().getTopInventory()
						, FancyBags.getVersionHandler().getBackpackSize(e.getPlayer().getItemInHand())
						, e.getView().getTitle(),
						FancyBags.getVersionHandler().getBackpackID(e.getPlayer().getItemInHand()));
				ItemMeta meta = backpack.getItemMeta();
				if (FancyBags.getInstance().getConfig().getBoolean("showContents")) {
					int slots = FancyBags.getVersionHandler().getBackpackSize(e.getPlayer().getItemInHand());
					ArrayList<String> lore = new ArrayList<String>();
					lore.add(Utils.color("&7A special portable bag with"));
					lore.add(Utils.color("&6" + slots  +" &7slots!"));
					lore.add(" ");

					if (!isEmpty(e.getView().getTopInventory())) {
						
						//Count and sort all the items in the backpack
						ArrayList<ItemStack> items = new ArrayList<ItemStack>();
						for (ItemStack item:e.getView().getTopInventory().getContents()) {
							if (item != null && item.getType() != Material.AIR && !item.equals(Utils.getRedVersionGlass())) {
								
								if (!items.isEmpty()) {
									boolean similar = false;
									for (ItemStack checkedItem:items) {
										if (item.isSimilar(checkedItem)) {
											checkedItem.setAmount(checkedItem.getAmount() + item.getAmount());
											similar = true;
										}
									}
									
									if (!similar) {
										items.add(item);
									}
									
								} else {
									items.add(item);
								}
							}
						}
						
						if (items.size() != 0) {
							if (!(items.size() > 5)) {
								for (ItemStack item:items) {
									String structure = FancyBags.getInstance().getConfig().getString("displayItemInLore");
									structure = structure.replace("{number}", item.getAmount() + "");
									
									if (item.getItemMeta().hasDisplayName()) {
										structure = structure.replace("{item_name}", item.getItemMeta().getDisplayName());
									} else {
										structure = structure.replace("{item_name}", "&f" + getName(item.getType()));
									}
									
									lore.add(Utils.color(structure));
								}
							} else {
								for (int i = 0; i < 5;i++) {
									ItemStack item = items.get(i);
									String structure = FancyBags.getInstance().getConfig().getString("displayItemInLore");
									structure = structure.replace("{number}", item.getAmount() + "");
									
									if (item.getItemMeta().hasDisplayName()) {
										structure = structure.replace("{item_name}", item.getItemMeta().getDisplayName());
									} else {
										structure = structure.replace("{item_name}", "&f" + getName(item.getType()));
									}
									
									lore.add(Utils.color(structure));
								}
								
								int amount = 0;
								for (int i = 5;i < items.size();i++) {
									amount += items.get(i).getAmount();
								}
								
								String other = FancyBags.getInstance().getConfig().getString("otherItemsInLore");
								other = other.replace("{amount}", amount + "");
								lore.add(Utils.color(other));
								
							}
						} else {
							lore.add(Utils.color(FancyBags.getInstance().getConfig().getString("emptyBackpack")));
						}
						

						
						
					} else {
						lore.add(Utils.color(FancyBags.getInstance().getConfig().getString("emptyBackpack")));
					}
					
					lore.add(" ");
					meta.setLore(lore);
				} else {
					meta.setLore(new ArrayList<String>());
				}
				backpack.setItemMeta(meta);
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
	
	
	private boolean isEmpty(Inventory inv) {
		for(ItemStack it : inv.getContents()) {
		    if(it != null) return false;
		}
		return true;
	}
	
	
	private String getName(Material mat) {
		String name = mat.name().replace('_',' ').toLowerCase();
		String[] data = name.split("");
		
		for (int i = 0;i < data.length;i++) {
			if (i != 0) {
				if (data[i - 1].equals(" ")) {
					data[i] = data[i].toUpperCase();
				}
			} else {
				data[i] = data[i].toUpperCase();
			}
		}
		
		name = arrayToString(data);
		return name;
	}
	
	private String arrayToString(String[] arr) {
		String str = "";
		for (String chr:arr) {
			str = str + chr;
		}
		return str;
	}
	
}
