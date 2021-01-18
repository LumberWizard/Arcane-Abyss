package dev.camscorner.arcaneabyss.common.spells.modifiers;

import dev.camscorner.arcaneabyss.api.spells.SpellModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class PotencyModifier extends SpellModifier
{
	public PotencyModifier(float costMultiplier)
	{
		super(costMultiplier);
	}

	@Override
	public Item getItemCost()
	{
		return Items.GLOWSTONE_DUST;
	}

	@Override
	public void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack)
	{

	}
}