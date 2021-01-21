package dev.camscorner.arcaneabyss.common.gui.slot;

import dev.camscorner.arcaneabyss.api.spells.SpellElement;
import dev.camscorner.arcaneabyss.api.spells.SpellModifier;
import dev.camscorner.arcaneabyss.api.spells.SpellShape;
import dev.camscorner.arcaneabyss.common.items.RunicStoneItem;
import dev.camscorner.arcaneabyss.core.registry.ModItems;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;

public class SpellComponentSlot extends Slot
{
	public SpellComponentSlot(Inventory inventory, int index, int x, int y)
	{
		super(inventory, index, x, y);
	}

	@Override
	public boolean canInsert(ItemStack stack)
	{
		ItemStack lastStack = inventory.getStack(id - 1);

		if(!(stack.getItem() instanceof RunicStoneItem) || lastStack.getItem() == Items.AIR)
			return false;

		RunicStoneItem newItem = (RunicStoneItem) stack.getItem();
		Item lastItem = lastStack.getItem();

		return (inventory.getStack(1).getItem() == ModItems.SPELL_PAPER && stack.getItem() == ModItems.RUNIC_STONE) &&
				lastStack.getItem() == ModItems.SPELL_PAPER ? newItem.getSpellComponent(stack) instanceof SpellShape :
				((RunicStoneItem) lastItem).getSpellComponent(lastStack) instanceof SpellShape ? newItem.getSpellComponent(stack) instanceof SpellElement :
				((RunicStoneItem) lastItem).getSpellComponent(lastStack) instanceof SpellElement ? (newItem.getSpellComponent(stack) instanceof SpellShape ||
				newItem.getSpellComponent(stack) instanceof SpellModifier) : ((RunicStoneItem) lastItem).getSpellComponent(lastStack) instanceof SpellModifier &&
				(newItem.getSpellComponent(stack) instanceof SpellModifier || newItem.getSpellComponent(stack) instanceof SpellShape);
	}

	@Override
	public boolean doDrawHoveringEffect()
	{
		return inventory.getStack(1).getItem() == ModItems.SPELL_PAPER;
	}
}
