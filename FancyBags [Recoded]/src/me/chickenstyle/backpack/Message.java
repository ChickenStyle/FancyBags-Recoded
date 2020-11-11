package me.chickenstyle.backpack;

import org.bukkit.ChatColor;

public enum Message {

    NO_PERMISSION(Color(getString("messages.noPermission"))),
    DISABLE_PLACE(Color(getString("messages.disablePlace"))),
    GIVE_MESSAGE(Color(getString("messages.giveMessage"))),
	DISABLE_CRAFT(Color(getString("messages.disableCraft"))),
	CANCEL_OPEN(Color(getString("messages.cancelOpen")));
    private String error;

    Message(String error) {
        this.error = error;
    }

    public String getMSG() {
        return error;
    }
    
    private static String Color(String text) {
    	return ChatColor.translateAlternateColorCodes('&', text);
    }
    
    private static String getString(String path) {
    	return FancyBags.getInstance().getConfig().getString(path);
    }
}