package me.chickenstyle.backpack.prompts;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import me.chickenstyle.backpack.Backpack;
import me.chickenstyle.backpack.FancyBags;
import me.chickenstyle.backpack.utilsfolder.Utils;

public class IdPrompt extends NumericPrompt{

	@Override
	public String getPromptText(ConversationContext context) {
		return Utils.color("&7Lets start creating new backpack! \nEnter a &6unique &7backpack id. (Should be a number!)");
			
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, Number number) {
		int id = Integer.valueOf(number.toString());
		Player player = (Player) context.getForWhom();
		player.sendMessage(Utils.color("&a" + id));
		Backpack pack = FancyBags.creatingBackpack.get(player.getUniqueId());
		pack.setId(id);
		FancyBags.creatingBackpack.put(player.getUniqueId(), pack);
		return new NamePrompt();
	}
	
	@Override
	protected String getFailedValidationText(ConversationContext context,Number input) {
		return Utils.color("&4 " + input + " is invalid id, please use a valid number!");
	}

}
