package me.chickenstyle.backpack.utilsfolder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.AuthorNagException;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import me.chickenstyle.backpack.Backpack;
import me.chickenstyle.backpack.FancyBags;
import me.chickenstyle.backpack.customholders.BackpackHolder;


public class Utils {
	
	
	public static String color(String text) {
		if (Bukkit.getVersion().contains("1.16")) {
			Pattern pattern = Pattern.compile("#[a-fA-f0-9]{6}");
			Matcher match = pattern.matcher(text);
			while (match.find()) {
				String color = text.substring(match.start(),match.end());
				text = text.replace(color, net.md_5.bungee.api.ChatColor.of(color) + "");
			}
		}

		return ChatColor.translateAlternateColorCodes('&', text);
	}
	
	public static int generateRandomNumber(int n) {
	    int m = (int) Math.pow(10, n - 1);
	    return m + new Random().nextInt(9 * m);
	}
	
	
    public static String inventoryToBase64(Inventory inventory) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeInt(inventory.getSize());
            for (int i = 0; i < inventory.getSize(); i++) {
                dataOutput.writeObject(inventory.getItem(i));
            }
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
            //Converts the inventory and its contents to base64, This also saves item meta-data and inventory type
        } catch (Exception e) {
            throw new IllegalStateException("Could not convert inventory to base64.", e);
        }
    }
    
    public static Inventory inventoryFromBase64(String data) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            Inventory inventory = Bukkit.getServer().createInventory(new BackpackHolder(), dataInput.readInt());
            // Read the serialized inventory
            for (int i = 0; i < inventory.getSize(); i++)
                inventory.setItem(i, (ItemStack) dataInput.readObject());
            dataInput.close();
            return inventory;
        }
        catch(ClassNotFoundException e) {
            throw new RuntimeException("Unable to decode the class type.", e);
        }
        catch(IOException e) {
            throw new RuntimeException("Unable to convert Inventory to Base64.", e);
        }
    }
    
    @SuppressWarnings("deprecation")
	public static ItemStack getVersionSkull() {
        if (Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.14") ||
        		Bukkit.getVersion().contains("1.15") || Bukkit.getVersion().contains("1.16")) {
            return new ItemStack(Material.valueOf("PLAYER_HEAD"));
        } else {
            return new ItemStack(Material.valueOf("SKULL_ITEM"), 1, (short) 3);
        }
    }
    
    @SuppressWarnings("deprecation")
	public static ItemStack getRedVersionGlass() {
    	ItemStack glass = null;
        if (Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.14") ||
        		Bukkit.getVersion().contains("1.15") || Bukkit.getVersion().contains("1.16")) {
            glass = new ItemStack(Material.valueOf("RED_STAINED_GLASS_PANE"));
        } else {
        	glass = new ItemStack(Material.valueOf("STAINED_GLASS_PANE"), 1, (short) 14);
        }
        
        ItemMeta meta = glass.getItemMeta();
        meta.setDisplayName(color(FancyBags.getInstance().getConfig().getString("slotsLimit")));
        glass.setItemMeta(meta);
        return glass;
    }
    
    @SuppressWarnings("deprecation")
	public static ItemStack getGreenVersionGlass() {
    	ItemStack glass = null;
        if (Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.14") ||
        		Bukkit.getVersion().contains("1.15") || Bukkit.getVersion().contains("1.16")) {
            glass = new ItemStack(Material.valueOf("GREEN_STAINED_GLASS_PANE"));
        } else {
        	glass = new ItemStack(Material.valueOf("STAINED_GLASS_PANE"), 1, (short) 13);
        }
        
        ItemMeta meta = glass.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Click here to save the recipe!");
        glass.setItemMeta(meta);
        return glass;
    }
    
    
    @SuppressWarnings("deprecation")
	public static ItemStack getGrayVersionGlass() {
    	ItemStack glass = null;
        if (Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.14") ||
        		Bukkit.getVersion().contains("1.15") || Bukkit.getVersion().contains("1.16")) {
            glass = new ItemStack(Material.valueOf("GRAY_STAINED_GLASS_PANE"));
        } else {
        	glass = new ItemStack(Material.valueOf("STAINED_GLASS_PANE"), 1, (short) 7);
        }
        
        ItemMeta meta = glass.getItemMeta();
        meta.setDisplayName(" ");
        glass.setItemMeta(meta);
        return glass;
    }
    
    public static Sound getVersionChestSound() {
    	Sound glass = null;
        if (Bukkit.getVersion().contains("1.8")) {
        	glass = Sound.valueOf("CHEST_OPEN");
        } else {
        	glass = Sound.valueOf("BLOCK_CHEST_OPEN");
        }
        return glass;
    }

    public static ItemStack createCustomSkull(String displayName, String texture) {
    	try {
            ItemStack skull = getVersionSkull();
            if (texture.isEmpty()) {
                return skull;
            }
            texture = "http://textures.minecraft.net/texture/" + texture;
            SkullMeta skullMeta = (SkullMeta)skull.getItemMeta();
            skullMeta.setDisplayName(Utils.color(displayName));
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            byte[] encodedData = java.util.Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", texture).getBytes());
            profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
            Field profileField = null;
            try {
                profileField = skullMeta.getClass().getDeclaredField("profile");
            }
            catch (NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
            }
            assert profileField != null;
            profileField.setAccessible(true);
            try {
                profileField.set(skullMeta, profile);
            }
            catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            skull.setItemMeta(skullMeta);
            return skull;
    	} catch (AuthorNagException e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    public static ItemStack createBackPack(String name,String texture,int slotsAmount,int id) {
    	ItemStack item = null;
    	
    	if (slotsAmount >= 0 && slotsAmount <= 9) {
        	item = FancyBags.getVersionHandler().addInventoryTag(createCustomSkull(name, texture),
        			Bukkit.createInventory(null, 9, "Dummy"),slotsAmount,name,id);
    	}
    	if (slotsAmount >= 10 && slotsAmount <= 18) {
    		item = FancyBags.getVersionHandler().addInventoryTag(createCustomSkull(name, texture),
        			Bukkit.createInventory(null, 18, "Dummy"),slotsAmount,name,id);
    	}
    	
    	if (slotsAmount >= 19 && slotsAmount <= 27) {
    		item = FancyBags.getVersionHandler().addInventoryTag(createCustomSkull(name, texture),
        			Bukkit.createInventory(null, 27, "Dummy"),slotsAmount,name,id);
    	}
    	
    	if (slotsAmount >= 28 && slotsAmount <= 36) {
    		item = FancyBags.getVersionHandler().addInventoryTag(createCustomSkull(name, texture),
        			Bukkit.createInventory(null, 36, "Dummy"),slotsAmount,name,id);
    	}
    	
    	if (slotsAmount >= 37 && slotsAmount <= 45) {
    		item = FancyBags.getVersionHandler().addInventoryTag(createCustomSkull(name, texture),
        			Bukkit.createInventory(null, 45, "Dummy"),slotsAmount,name,id);
    	}
    	
    	if (slotsAmount >= 46 && slotsAmount <= 54) {
    		item = FancyBags.getVersionHandler().addInventoryTag(createCustomSkull(name, texture),
        			Bukkit.createInventory(null, 54, "Dummy"),slotsAmount,name,id);
    	}
    	
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(Utils.color("&7A special portable bag with"));
		lore.add(Utils.color("&6" + slotsAmount  +" &7slots!"));
		lore.add(" ");
		lore.add(Utils.color(FancyBags.getInstance().getConfig().getString("emptyBackpack")));
		lore.add(" ");
    	
    	ItemMeta meta = item.getItemMeta();
    	meta.setLore(lore);
    	item.setItemMeta(meta);
    	
    	return item;
    	

    }
    
    public static ItemStack createBackPack(Backpack pack) {
    	ItemStack item = null;
    	if (pack.getSlotsAmount() >= 0 && pack.getSlotsAmount() <= 9) {
    		item = FancyBags.getVersionHandler().addInventoryTag(createCustomSkull(pack.getName(), pack.getTexture()),
        			Bukkit.createInventory(null, 9, "Dummy"),pack.getSlotsAmount(),pack.getName(),pack.getId());
    	}
    	if (pack.getSlotsAmount() >= 10 && pack.getSlotsAmount() <= 18) {
    		item = FancyBags.getVersionHandler().addInventoryTag(createCustomSkull(pack.getName(), pack.getTexture()),
        			Bukkit.createInventory(null, 18, "Dummy"),pack.getSlotsAmount(),pack.getName(),pack.getId());
    	}
    	
    	if (pack.getSlotsAmount() >= 19 && pack.getSlotsAmount() <= 27) {
    		item = FancyBags.getVersionHandler().addInventoryTag(createCustomSkull(pack.getName(), pack.getTexture()),
        			Bukkit.createInventory(null, 27, "Dummy"),pack.getSlotsAmount(),pack.getName(),pack.getId());
    	}
    	
    	if (pack.getSlotsAmount() >= 28 && pack.getSlotsAmount() <= 36) {
    		item = FancyBags.getVersionHandler().addInventoryTag(createCustomSkull(pack.getName(), pack.getTexture()),
        			Bukkit.createInventory(null, 36, "Dummy"),pack.getSlotsAmount(),pack.getName(),pack.getId());
    	}
    	
    	if (pack.getSlotsAmount() >= 37 && pack.getSlotsAmount() <= 45) {
    		item = FancyBags.getVersionHandler().addInventoryTag(createCustomSkull(pack.getName(), pack.getTexture()),
        			Bukkit.createInventory(null, 45, "Dummy"),pack.getSlotsAmount(),pack.getName(),pack.getId());
    	}
    	
    	if (pack.getSlotsAmount() >= 46 && pack.getSlotsAmount() <= 54) {
    		item = FancyBags.getVersionHandler().addInventoryTag(createCustomSkull(pack.getName(), pack.getTexture()),
        			Bukkit.createInventory(null, 54, "Dummy"),pack.getSlotsAmount(),pack.getName(),pack.getId());
    	}
    	
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(Utils.color("&7A special portable bag with"));
		lore.add(Utils.color("&6" + pack.getSlotsAmount()  +" &7slots!"));
		lore.add(" ");
		lore.add(Utils.color(FancyBags.getInstance().getConfig().getString("emptyBackpack")));
		lore.add(" ");
    	
    	ItemMeta meta = item.getItemMeta();
    	meta.setLore(lore);
    	item.setItemMeta(meta);
    	
    	return item;
    }
    

    
}
