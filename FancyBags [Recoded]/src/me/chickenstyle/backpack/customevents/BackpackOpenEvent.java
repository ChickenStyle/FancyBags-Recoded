package me.chickenstyle.backpack.customevents;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;

public class BackpackOpenEvent extends Event implements Cancellable{

	private final Player player;
	private final Inventory inv;
	private boolean cancelled;
	
	private static final HandlerList HANDLERS = new HandlerList();
	
	public BackpackOpenEvent(Player player,Inventory inv) {
		this.player = player;
		this.inv = inv;
		cancelled = false;
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

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean result) {
		cancelled = result;
		
	}

}
