package dev.camscorner.arcaneabyss.core.registry;

import dev.camscorner.arcaneabyss.common.items.StaffItem;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class ModEvents
{
	public static void clientEvents()
	{
		//-----Tick Event-----//
		ClientTickEvents.END_CLIENT_TICK.register(new ClientTickEvents.EndTick()
		{
			private int spellMenuTicks = 0;

			@Override
			public void onEndTick(MinecraftClient client)
			{
				if(client.player != null && client.currentScreen == null)
				{
					if(client.player.getMainHandStack().getItem() instanceof StaffItem ||
							client.player.getOffHandStack().getItem() instanceof StaffItem)
					{
						if(ModKeybinds.SPELL_MENU.isPressed())
						{
							if(client.mouse.isCursorLocked())
								client.mouse.unlockCursor();

							if(spellMenuTicks < 10)
								++spellMenuTicks;
						}
						else
						{
							if(!client.mouse.isCursorLocked())
							{
								client.mouse.lockCursor();
							}

							if(spellMenuTicks > 0)
								--spellMenuTicks;
						}
					}
					else
					{
						if(!client.mouse.isCursorLocked())
						{
							client.mouse.lockCursor();
						}

						if(spellMenuTicks > 0)
							--spellMenuTicks;
					}
				}
			}
		});
	}
}
