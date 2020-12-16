package dev.camscorner.arcaneabyss.api.spells;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public abstract class SpellShape implements SpellComponent
{
	private double costMultiplier;
	private boolean isInstant;

	public SpellShape(double costMultiplier, boolean isInstant)
	{
		this.costMultiplier = costMultiplier;
		this.isInstant = isInstant;
	}

	public abstract void onRightClick(PlayerEntity caster, World world, Hand hand, ItemStack stack);

	public double getCostMultiplier()
	{
		return this.costMultiplier;
	}

	public boolean isInstant()
	{
		return this.isInstant;
	}
}
