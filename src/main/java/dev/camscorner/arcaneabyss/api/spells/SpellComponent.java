package dev.camscorner.arcaneabyss.api.spells;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

public interface SpellComponent
{
	default Item getItemCost()
	{
		return Items.AIR;
	}
}
