package dev.camscorner.arcaneabyss.common.gui.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemSpecificSlot extends Slot
{
	private List<Item> items = new ArrayList<>();

	public ItemSpecificSlot(Inventory inventory, int index, int x, int y, Item... items)
	{
		super(inventory, index, x, y);
		this.items.addAll(Arrays.asList(items));
	}

	@Override
	public boolean canInsert(ItemStack stack)
	{
		return items.contains(stack.getItem());
	}
}
