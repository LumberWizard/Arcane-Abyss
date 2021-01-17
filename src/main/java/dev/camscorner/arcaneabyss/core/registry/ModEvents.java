package dev.camscorner.arcaneabyss.core.registry;

import dev.camscorner.arcaneabyss.common.items.StaffItem;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.Hand;

public class ModEvents
{
	public static void clientEvents()
	{
		//-----Tick Event-----//
		ClientTickEvents.END_CLIENT_TICK.register(client ->
		{
			if(client.player != null && client.currentScreen == null)
			{
				if(client.player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof StaffItem || client.player.getStackInHand(Hand.OFF_HAND).getItem() instanceof StaffItem)
				{
					if(ModKeybinds.SPELL_MENU.isPressed())
					{
						if(client.mouse.isCursorLocked())
							client.mouse.unlockCursor();
					}
					else
					{
						if(!client.mouse.isCursorLocked())
							client.mouse.lockCursor();
					}
				}
				else if(!client.mouse.isCursorLocked())
					client.mouse.lockCursor();
			}
		});
	}
}
