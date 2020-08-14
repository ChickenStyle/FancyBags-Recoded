package me.chickenstyle.backpack;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;



public class RejectItems {
	private boolean reject;
	private RejectType type;
	private ArrayList<ItemStack> items;
	
	public RejectItems(boolean reject, RejectType type, ArrayList<ItemStack> items) {
		this.reject = reject;
		this.type = type;
		this.items = items;
	}

	public boolean isRejecting() {
		return reject;
	}

	public void setRejecting(boolean reject) {
		this.reject = reject;
	}

	public RejectType getType() {
		return type;
	}

	public void setType(RejectType type) {
		this.type = type;
	}

	public ArrayList<ItemStack> getItems() {
		return items;
	}

	public void setItems(ArrayList<ItemStack> items) {
		this.items = items;
	}
	
	
	
	
	
}
