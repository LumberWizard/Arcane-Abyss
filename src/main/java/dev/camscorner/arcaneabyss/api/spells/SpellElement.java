package dev.camscorner.arcaneabyss.api.spells;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public abstract class SpellElement implements SpellComponent
{
	public float costMultiplier;

	public SpellElement(float costMultiplier)
	{
		this.costMultiplier = costMultiplier;
	}

	public abstract void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack);

	public float getCostMultiplier()
	{
		return this.costMultiplier;
	}
}
