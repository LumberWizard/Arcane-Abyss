package dev.camscorner.arcaneabyss.core.registry;

import dev.camscorner.arcaneabyss.common.items.SpellCrystalItem;
import dev.camscorner.arcaneabyss.common.items.StaffItem;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ModEvents
{
	public static int spellMenuTicks = 0;
	private static ItemStack stack = null;

	public static void setStack(ItemStack stack)
	{
		ModEvents.stack = stack;
	}

	public static List<ItemStack> filteredPlayerItems(PlayerEntity player)
	{
		List<ItemStack> list = new ArrayList<>();

		for(int i = 0; i < player.inventory.size(); i++)
			if(player.inventory.getStack(i).getItem() instanceof SpellCrystalItem)
				if(player.inventory.getStack(i).getOrCreateTag().contains("Component_1"))
					list.add(player.inventory.getStack(i));

		return list;
	}

	public static void clientEvents()
	{
		//-----Client Tick Event-----//
		ClientTickEvents.END_CLIENT_TICK.register(client ->
		{
			if(client.player != null)
			{
				PlayerEntity player = client.player;

				if(stack != null)
				{
					//player.sendMessage(stack.getName(), false);
					stack = null;
				}

				if(client.currentScreen == null)
				{
					if(player.getMainHandStack().getItem() instanceof StaffItem ||
							player.getOffHandStack().getItem() instanceof StaffItem)
					{
						if(ModKeybinds.SPELL_MENU.isPressed())
						{
							if(!player.isSneaking() && !filteredPlayerItems(player).isEmpty())
							{
								if(client.mouse.isCursorLocked())
									client.mouse.unlockCursor();

								if(spellMenuTicks < 5)
									++spellMenuTicks;
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
