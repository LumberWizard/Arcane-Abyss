package dev.camscorner.arcaneabyss.common.spells.modifiers;

import dev.camscorner.arcaneabyss.api.spells.SpellModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class FireElementalModifier extends SpellModifier
{
	public FireElementalModifier(float costMultiplier)
	{
		super(costMultiplier);
	}

	@Override
	public void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack)
	{

	}
}
