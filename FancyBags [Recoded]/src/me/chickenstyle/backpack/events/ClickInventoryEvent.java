package me.chickenstyle.backpack.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import me.chickenstyle.backpack.Backpack;
import me.chickenstyle.backpack.FancyBags;
import me.chickenstyle.backpack.Message;
import me.chickenstyle.backpack.RejectType;
import me.chickenstyle.backpack.configs.CustomBackpacks;
import me.chickenstyle.backpack.customholders.BackpackHolder;
import me.chickenstyle.backpack.customholders.CreateRecipeHolder;
import me.chickenstyle.backpack.customholders.RejectItemsHolder;
import me.chickenstyle.backpack.guis.CreateRecipeGui;
import me.chickenstyle.backpack.utilsfolder.Utils;


public class ClickInventoryEvent implements Listener{
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClickInventory(InventoryClickEvent e) {
		if (e.getInventory() == null) return;
		Player player = (Player) e.getWhoClicked();
		if (e.getView().getTopInventory().getHolder() instanceof BackpackHolder) {
			
			if (e.getClick().equals(ClickType.NUMBER_KEY)) {
				e.setCancelled(true);
			}
			
			if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {return;}
			
			if (FancyBags.getVersionHandler().hasInventoryTag(e.getCurrentItem())) {
				e.setCancelled(true);
				player.sendMessage(Message.DISABLE_PLACE.getMSG());
				return;
			}
				if (CustomBackpacks.hasBackpack(FancyBags.getVersionHandler().getBackpackID(player.getItemInHand()))) {
					int id = FancyBags.getVersionHandler().getBackpackID(player.getItemInHand());
					Backpack pack = CustomBackpacks.getBackpack(id);
					
					if (pack.getReject().isRejecting()) {
						if (!pack.getReject().getItems().isEmpty()) {
							if (pack.getReject().getType().equals(RejectType.BLACKLIST)) {
								for (ItemStack item:pack.getReject().getItems()) {
									
									if (!FancyBags.getVersionHandler().hasInventoryTag(e.getCurrentItem())) {
										if (isSimilar(item,e.getCurrentItem())) {
											e.setCancelled(true);
											player.sendMessage(Message.DISABLE_PLACE.getMSG());
											break;
										}
									}

							  }
						}
						
						if (pack.getReject().getType().equals(RejectType.WHITELIST)) {
								boolean contains = false;
								for (ItemStack item:pack.getReject().getItems()) {
									if (isSimilar(item,e.getCurrentItem())) {
										contains = true;
									}
								}
								if (!FancyBags.getVersionHandler().hasInventoryTag(e.getCurrentItem())) {
									if (contains == false) {
										e.setCancelled(true);
										player.sendMessage(Message.DISABLE_PLACE.getMSG());
									}
								}
							}
						}
					}	
				}


			
			
			
			if (e.getCurrentItem().equals(Utils.getRedVersionGlass())) {
				e.setCancelled(true);
			}
		}
		
		if (e.getInventory().getHolder() instanceof CreateRecipeHolder) {
			Backpack pack = FancyBags.creatingBackpack.get(player.getUniqueId());
			if (e.getCurrentItem() == null) {
				return;
			}
			
			if (e.getCurrentItem().equals(Utils.getGrayVersionGlass())) {
				e.setCancelled(true);
			}
			if (e.getCurrentItem().equals(Utils.getGreenVersionGlass())) {
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

				ArrayList<ItemStack> materials = new ArrayList<ItemStack>();
				int airAmount = 0;
				for (Integer i:emptySlots) {
					if (e.getInventory().getItem(i) == null || e.getInventory().getItem(i).getType().equals(Material.AIR)) {
						materials.add(new ItemStack(Material.AIR));
						airAmount++;
					} else {
						materials.add(e.getInventory().getItem(i));
					}
				}


				if (airAmount == 9) {
					pack.setRecipe(null);
					CustomBackpacks.addBackpack(pack,new HashMap<Character,ItemStack>());
					FancyBags.creatingBackpack.remove(player.getUniqueId());
					player.sendMessage(ChatColor.GREEN + "Backpack has been created! type /fb reload to load the recipe!");
					e.setCancelled(true);
					player.closeInventory();
					return;
				}
				
				HashMap<Character,ItemStack> ingredients = new HashMap<Character,ItemStack>();
				for (ItemStack mat:materials) {
					boolean contains = false;	
					do {
						Random r = new Random();
						char symbol = (char)(r.nextInt(26) + 'a');
						if (!ingredients.isEmpty()) {
							if (!ingredients.containsKey(symbol)) {
								boolean containsMaterial = false;
								for (Entry<Character, ItemStack> entry : ingredients.entrySet()) {
								    if (entry.getValue().equals(mat)) {
								    	containsMaterial = true;
								    }
								}
								
								if (containsMaterial == false) {
									ingredients.put(symbol, mat);
								} 
								contains = true;
							}
						} else {
							ingredients.put(symbol, mat);
							contains = true;
						}
						
					} while (contains == false);
				}
				
				char matAir = 0;
				ArrayList<Character> symbols = new ArrayList<Character>(); 
				for (ItemStack mat:materials) {
					for (Entry<Character, ItemStack> entry : ingredients.entrySet()) {
					    if (mat.equals(entry.getValue())) {
					    	symbols.add(entry.getKey());
					    }
					    if (entry.getValue().getType().equals(Material.AIR)) {
					    	matAir = entry.getKey();
					    }
					}
				}
				
				
				
				String firstLine = "";
				String secondLine = "";
				String thirdLine = "";
				for (int i = 0; i < 3;i++) firstLine = (firstLine + symbols.get(i)).replace(matAir, ' ');
				for (int i = 3; i < 6;i++) secondLine = (secondLine + symbols.get(i)).replace(matAir, ' ');
				for (int i = 6; i < 9;i++) thirdLine = (thirdLine + symbols.get(i)).replace(matAir, ' ');
				
				
				ItemStack bagItem = Utils.createBackPack(pack.getName(), pack.getTexture(), pack.getSlotsAmount(),pack.getId());
				
        		ShapedRecipe recipe;
        		if (Bukkit.getVersion().contains("1.12") ||
        			Bukkit.getVersion().contains("1.13") ||
        			Bukkit.getVersion().contains("1.14") ||
        			Bukkit.getVersion().contains("1.15") ||
        			Bukkit.getVersion().contains("1.16")) {
        			recipe = new ShapedRecipe(new NamespacedKey(FancyBags.getInstance(),"key"),bagItem);
        		} else {
        			recipe = new ShapedRecipe(bagItem);
        		}
				
				recipe.shape(firstLine,secondLine,thirdLine);
				
				for (Entry<Character, ItemStack> entry : ingredients.entrySet()) {
					if (!entry.getValue().getType().equals(Material.AIR) && !(entry.getValue() == null)) {
						recipe.setIngredient(entry.getKey(), entry.getValue().getData());	
					}
				}
				
				pack.setRecipe(recipe);
				CustomBackpacks.addBackpack(pack,ingredients);
				FancyBags.creatingBackpack.remove(player.getUniqueId());
				player.sendMessage(ChatColor.GREEN + "Backpack has been added! type /fb reload to load the recipe!");
				e.setCancelled(true);
				player.closeInventory();
			}
			
		}
		
		if (e.getInventory().getHolder() instanceof RejectItemsHolder) {
			ItemStack green = Utils.getGreenVersionGlass();
			ItemMeta meta = green.getItemMeta();
			meta.setDisplayName(Utils.color("&aClick here to save the blacklist/whitelist!"));
			green.setItemMeta(meta);

			if (e.getCurrentItem() != null && !e.getCurrentItem().getType().equals(Material.AIR)) {
				if (e.getCurrentItem().isSimilar(green)) {
					e.setCancelled(true);
					ArrayList<ItemStack> items = new ArrayList<ItemStack>();
					ItemStack[] contents = e.getView().getTopInventory().getContents();
					for (int i = 0;i < 53;i++) {
						if (contents[i] != null && !contents[i].getType().equals(Material.AIR)) {
							items.add(contents[i]);
						}
					}
					
					Backpack pack = FancyBags.creatingBackpack.get(player.getUniqueId());
					pack.getReject().setItems(items);
					FancyBags.creatingBackpack.put(player.getUniqueId(), pack);
					
					player.sendMessage(Utils.color("&aOkey, and the now lets create the recipes!"));
					
					new CreateRecipeGui(player);
				}
			}
		}
		

	}
	
	@SuppressWarnings("deprecation")
	public static boolean isSimilar(ItemStack a, ItemStack b) {
		
		
	    if(a == null || b == null)
	        return false;
	    
	    if(a.getType() != b.getType())
	        return false;

		if (Bukkit.getVersion().contains("1.8") ||
			Bukkit.getVersion().contains("1.9") ||
			Bukkit.getVersion().contains("1.10")||
			Bukkit.getVersion().contains("1.11")||
			Bukkit.getVersion().contains("1.12")) {
			ItemStack item = new ItemStack(b.getType(),1,b.getData().getData());
			
			if (!item.getType().isBlock()) {
				item.setDurability((short) 0);
				
				
				if (a.getData().getData() != item.getData().getData()) {
					return false;
				} 
			} else {
				if (a.getData().getData() != item.getData().getData()) {
					return false;
				} 
			}
			
			
		}
	    

	    ItemMeta first = a.getItemMeta();
	    ItemMeta second = b.getItemMeta();
	    

	    if (first.hasDisplayName() != second.hasDisplayName())
	    	return false;
	    
	    if (first.hasDisplayName() && second.hasDisplayName()) {
		    if (!first.getDisplayName().equals(second.getDisplayName()))
		    	return false;
	    }
	    
	    
	    if (first.hasLore() && second.hasLore()) {
		    if (!first.getLore().equals(second.getLore())) 
		    	return false;
	    }
	    
	    if (!first.getEnchants().equals(second.getEnchants()))
	    	return false;  
	    
	    return true;
	}
	
}
