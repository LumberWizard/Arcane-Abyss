package dev.camscorner.arcaneabyss.common.spells.modifiers;

import dev.camscorner.arcaneabyss.api.spells.SpellModifier;
import dev.camscorner.arcaneabyss.core.registry.AAItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class VoidElementalModifier extends SpellModifier
{
	public VoidElementalModifier(float costMultiplier, int maxLevel)
	{
		super(costMultiplier, maxLevel, "r");
	}

	@Override
	public Item getItemCost()
	{
		return AAItems.ENTROPIC_CRYSTAL;
	}

	@Override
	public int getColour()
	{
		return 0x252525;
	}

	@Override
	public void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack)
	{

	}
}
