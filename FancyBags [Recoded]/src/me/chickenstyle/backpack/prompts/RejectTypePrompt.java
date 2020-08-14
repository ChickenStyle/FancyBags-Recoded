package me.chickenstyle.backpack.prompts;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import me.chickenstyle.backpack.Backpack;
import me.chickenstyle.backpack.FancyBags;
import me.chickenstyle.backpack.RejectType;
import me.chickenstyle.backpack.guis.RejectItemsGui;
import me.chickenstyle.backpack.utilsfolder.Utils;

public class RejectTypePrompt extends StringPrompt{

	@Override
	public String getPromptText(ConversationContext context) {
		return Utils.color("&7Enter a reject items type (whitelist or blacklist)!");
	}
	
	@Override
	public Prompt acceptInput(ConversationContext context, String input) {
		try {
			RejectType type = RejectType.valueOf(input.toUpperCase());
			context.getForWhom().sendRawMessage(Utils.color("&7Good, now lets set the blacklist/whitelist items!"));
			Player player = (Player) context.getForWhom();

			Backpack pack = FancyBags.creatingBackpack.get(player.getUniqueId());
			pack.getReject().setType(type);
			FancyBags.creatingBackpack.put(player.getUniqueId(), pack);
			new RejectItemsGui(player);
			return Prompt.END_OF_CONVERSATION;
		} catch(Exception e) {
			context.getForWhom().sendRawMessage(Utils.color("&cInvalid reject items type! (Type whitelist/blacklist)"));
			return new RejectTypePrompt();
		}
		
		
		

		
	}

}
