package dev.camscorner.arcaneabyss.common.spells.modifiers;

import dev.camscorner.arcaneabyss.api.spells.SpellModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class SizeModifier extends SpellModifier
{
	public SizeModifier(float costMultiplier, int maxLevel)
	{
		super(costMultiplier, maxLevel, "l");
	}

	@Override
	public Item getItemCost()
	{
		return Items.MAGMA_CREAM;
	}

	@Override
	public int getColour()
	{
		return 0xB41467;
	}

	@Override
	public void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack)
	{

	}
}
