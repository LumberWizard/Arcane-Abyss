package dev.camscorner.arcaneabyss.common.spells.modifiers;

import dev.camscorner.arcaneabyss.api.spells.SpellModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class SplitModifier extends SpellModifier
{
	public SplitModifier(float costMultiplier, int maxLevel)
	{
		super(costMultiplier, maxLevel, "m");
	}

	@Override
	public Item getItemCost()
	{
		return Items.SLIME_BALL;
	}

	@Override
	public int getColour()
	{
		return 0x008B64;
	}

	@Override
	public void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack)
	{

	}
}
