package dev.camscorner.arcaneabyss.common.spells.shapes;

import dev.camscorner.arcaneabyss.api.spells.SpellShape;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ProjectileShape extends SpellShape
{
	public ProjectileShape(float costMultiplier, boolean isInstant)
	{
		super(costMultiplier, isInstant);
	}

	@Override
	public void onRightClick(PlayerEntity caster, World world, Hand hand, ItemStack stack)
	{

	}
}
