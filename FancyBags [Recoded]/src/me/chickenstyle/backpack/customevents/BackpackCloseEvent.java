package me.chickenstyle.backpack.customevents;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;

public class BackpackCloseEvent extends Event{

	
	private final Player player;
	private final Inventory inv;
	
	private static final HandlerList HANDLERS = new HandlerList();
	
	public BackpackCloseEvent(Player player,Inventory inv) {
		this.player = player;
		this.inv = inv;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Inventory getInventory() {
		return inv;
	}
	
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}


}
