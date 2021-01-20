package dev.camscorner.arcaneabyss.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ResearchScrollItem extends Item
{
	public ResearchScrollItem(Settings settings)
	{
		super(settings);
	}

	@Override
	public String getTranslationKey(ItemStack stack)
	{
		if(stack.getOrCreateTag().getBoolean("Known"))
			return super.getTranslationKey(stack) + ".known";
		else
			return super.getTranslationKey(stack) + ".unknown";
	}
}
