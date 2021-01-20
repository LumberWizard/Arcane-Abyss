package dev.camscorner.arcaneabyss.common.gui.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;

public class FancySlot extends Slot
{
	public FancySlot(Inventory inventory, int index, int x, int y)
	{
		super(inventory, index, x, y);
	}

	@Override
	public boolean canInsert(ItemStack stack)
	{
		return inventory.getStack(1).getItem() == Items.PAPER;
	}

	@Override
	public boolean doDrawHoveringEffect()
	{
		return inventory.getStack(1).getItem() == Items.PAPER;
	}
}
