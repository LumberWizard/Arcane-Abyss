package dev.camscorner.arcaneabyss.api.spells;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public interface SpellComponent
{
	Identifier DEFAULT_FONT = new Identifier(ArcaneAbyss.MOD_ID, "spell_icons");

	default Item getItemCost()
	{
		return Items.AIR;
	}

	default int getColour()
	{
		return 0xFFFFFF;
	}
}
