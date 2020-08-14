package me.chickenstyle.backpack.prompts;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import me.chickenstyle.backpack.Backpack;
import me.chickenstyle.backpack.FancyBags;
import me.chickenstyle.backpack.utilsfolder.Utils;

public class TexturePrompt extends StringPrompt{

	@Override
	public String getPromptText(ConversationContext context) {
		return Utils.color("&7Enter backpack's texture\n (to get the texture go to &6https://minecraft-heads.com/custom-heads &7 \n"
				+ "choose a texture,"
				+ "copy the 'Minecraft-URL' part and paste it here)");
	}
	
	@Override
	public Prompt acceptInput(ConversationContext context, String input) {
		Player player = (Player) context.getForWhom();
		player.sendMessage(Utils.color("&a" + input));
		Backpack pack = FancyBags.creatingBackpack.get(player.getUniqueId());
		pack.setTexture(input);
		FancyBags.creatingBackpack.put(player.getUniqueId(), pack);
		return new RejectPrompt();
	}

}
