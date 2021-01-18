package dev.camscorner.arcaneabyss.common.spells.modifiers;

import dev.camscorner.arcaneabyss.api.spells.SpellModifier;
import dev.camscorner.arcaneabyss.core.registry.ModItems;
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
		return ModItems.ENTROPIC_CRYSTAL;
	}

	@Override
	public void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack)
	{

	}
}
