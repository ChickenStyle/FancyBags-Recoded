package me.chickenstyle.backpack;

import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;

import me.chickenstyle.backpack.configs.CustomBackpacks;
import me.chickenstyle.backpack.prompts.IdPrompt;
import me.chickenstyle.backpack.utilsfolder.Utils;
import net.md_5.bungee.api.ChatColor;

public class BackpackCommand implements CommandExecutor {
	
	private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
	 
	public boolean isInt(String strNum) {
	    if (strNum == null) {
	        return false; 
	    }
	    return pattern.matcher(strNum).matches();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			if (args.length >= 1) {
				switch (args[0].toString()) {
				case "addbackpack":
					if (sender instanceof Player) {
						Player player = (Player) sender;
						if (player.hasPermission("FancyBags.Admin") || player.hasPermission("FancyBags." + args[0].toString())) {
							FancyBags.creatingBackpack.put(player.getUniqueId(), new Backpack(0, "", "", 0, null, null));
							ConversationFactory factory = new ConversationFactory(FancyBags.getInstance());
							Conversation conversation = factory.withFirstPrompt(new IdPrompt()).withLocalEcho(true).buildConversation(player);
							conversation.begin();
						} else {
							player.sendMessage(Message.NO_PERMISSION.getMSG());
						}
					}

				break;
				
				case "reload":
					if (sender.hasPermission("FancyBags.Admin") || sender.hasPermission("FancyBags." + args[0].toString())) {
						CustomBackpacks.configReload();
						FancyBags.getInstance().reloadConfig();
						FancyBags.getInstance().loadRecipes();
						sender.sendMessage(ChatColor.GREEN + "Configs and recipes have been reloaded!");
						
					} else {
						sender.sendMessage(Message.NO_PERMISSION.getMSG());
					}
				break;
				
				case "give":
					if (sender.hasPermission("FancyBags.Admin") || sender.hasPermission("FancyBags." + args[0].toString())) {
						if (args.length == 3) {
							if (Bukkit.getServer().getOnlinePlayers().contains(Bukkit.getPlayer(args[1]))) {
								if (isInt(args[2])) {
									if (CustomBackpacks.hasBackpack(Integer.valueOf(args[2]))) {
										Player target = Bukkit.getPlayer(args[1]);
										Backpack bag = CustomBackpacks.getBackpack(Integer.valueOf(args[2]));		
										
										if (target.getInventory().firstEmpty() != -1) {
											target.getInventory().addItem(FancyBags.getVersionHandler().addRandomTag(Utils.createBackPack(bag)));
										} else {
											target.getWorld().dropItemNaturally(target.getLocation(), FancyBags.getVersionHandler().addRandomTag(Utils.createBackPack(bag)));
										}
										
										target.sendMessage(Utils.color(Message.GIVE_MESSAGE.getMSG().replace("{player}", sender.getName())
												.replace("{backpack}", bag.getName())));
										
										sender.sendMessage(Utils.color("&aYou gave &6" + bag.getName() + " &ato &6" + target.getDisplayName()));
										
									} else {
										sender.sendMessage(ChatColor.RED + "There is no backpack with this id!");
									}
								} else {
									sender.sendMessage(ChatColor.RED + "Invalid backpack id!");
								}
							} else {
								sender.sendMessage(ChatColor.RED + "This player is offline :(");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "Invalid usage");
							sender.sendMessage(ChatColor.GRAY + "/fb give {player} {backpack ID}");
						}			
					} else {
						sender.sendMessage(Message.NO_PERMISSION.getMSG());
					}
				break;
				
				case "help":
					if (sender.hasPermission("FancyBags.Admin") || sender.hasPermission("FancyBags." + args[0].toString())) {
						sender.sendMessage(Utils.color("&f----------[&6FancyBags&f]----------"));
						sender.sendMessage(ChatColor.WHITE + "/fb give {player} {backpack_id}");
						sender.sendMessage(ChatColor.WHITE + "");
						sender.sendMessage(ChatColor.WHITE + "/fb addbackpack");
						sender.sendMessage(ChatColor.WHITE + "");
						sender.sendMessage(ChatColor.WHITE + "/fb reload");
						sender.sendMessage(ChatColor.WHITE + "");
						sender.sendMessage(ChatColor.WHITE + "/fb help");
						sender.sendMessage(ChatColor.WHITE + "------------------------------");
					} else {
						sender.sendMessage(Message.NO_PERMISSION.getMSG());
					}
				break;
					
				default:
					sender.sendMessage(ChatColor.GRAY + "Use /fb help to see all the command!");
				}
			} else {
				sender.sendMessage(ChatColor.GRAY + "Use /fb help to see all the command!");
			}
				

		return false;
	}

}
