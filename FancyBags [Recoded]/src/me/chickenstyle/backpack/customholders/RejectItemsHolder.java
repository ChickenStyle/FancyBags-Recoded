package me.chickenstyle.backpack.customholders;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class RejectItemsHolder implements InventoryHolder {  
    @Override
    public Inventory getInventory() {
        return null;
    }
}
