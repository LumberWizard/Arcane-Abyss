package dev.camscorner.arcaneabyss.api.spells;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public abstract class SpellModifier implements SpellComponent
{
	public double costMultiplier;

	public SpellModifier(double costMultiplier)
	{
		this.costMultiplier = costMultiplier;
	}

	public abstract void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack);

	public double getCostMultiplier()
	{
		return this.costMultiplier;
	}
}
