package dev.camscorner.arcaneabyss.common.gui;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.common.gui.slot.HideableSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class InscriptionTableScreenHandler extends ScreenHandler
{
	public final Inventory inventory;

	public InscriptionTableScreenHandler(int syncId, PlayerInventory playerInventory)
	{
		this(syncId, playerInventory, new SimpleInventory(8));
	}

	public InscriptionTableScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory)
	{
		super(ArcaneAbyss.INSCRIPTION_TABLE_SCREEN_HANDLER, syncId);
		checkSize(inventory, 8);
		this.inventory = inventory;
		inventory.onOpen(playerInventory.player);

		//This will place the slot in the correct locations for a 3x3 Grid. The slots exist on both server and client!
		//This will not render the background of the slots however, this is the Screens job
		int m;
		int l;

		addSlot(new Slot(inventory, 0, 8, 7));
		addSlot(new Slot(inventory, 1, 152, 7));

		for(m = 0; m < 1; ++m)
		{
			for(l = 0; l < 6; ++l)
			{
				addSlot(new HideableSlot(inventory, (l + 2) + m * 3, 35 + l * 18, 101 + m * 18));
			}
		}

		//The player inventory
		for(m = 0; m < 3; ++m)
		{
			for(l = 0; l < 9; ++l)
			{
				this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 127 + m * 18));
			}
		}

		//The player Hotbar
		for(m = 0; m < 9; ++m)
		{
			this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 185));
		}
	}

	@Override
	public boolean canUse(PlayerEntity player)
	{
		return this.inventory.canPlayerUse(player);
	}

	// Shift + Player Inv Slot
	@Override
	public ItemStack transferSlot(PlayerEntity player, int invSlot)
	{
		ItemStack newStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(invSlot);

		if(slot != null && slot.hasStack())
		{
			ItemStack originalStack = slot.getStack();
			newStack = originalStack.copy();

			if(invSlot < this.inventory.size())
			{
				if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if(!this.insertItem(originalStack, 0, this.inventory.size(), false))
			{
				return ItemStack.EMPTY;
			}

			if(originalStack.isEmpty())
			{
				slot.setStack(ItemStack.EMPTY);
			}
			else
			{
				slot.markDirty();
			}
		}

		return newStack;
	}
}
