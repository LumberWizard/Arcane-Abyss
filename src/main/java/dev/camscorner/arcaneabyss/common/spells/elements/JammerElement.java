package dev.camscorner.arcaneabyss.common.spells.elements;

import dev.camscorner.arcaneabyss.api.spells.SpellElement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class JammerElement extends SpellElement
{
	public JammerElement(float costMultiplier)
	{
		super(costMultiplier);
	}

	@Override
	public void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack)
	{

	}
}
