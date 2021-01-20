package dev.camscorner.arcaneabyss.common.spells.modifiers;

import dev.camscorner.arcaneabyss.api.spells.SpellModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class WaterElementalModifier extends SpellModifier
{
	public WaterElementalModifier(float costMultiplier, int maxLevel)
	{
		super(costMultiplier, maxLevel, "n");
	}

	@Override
	public Item getItemCost()
	{
		return Items.PRISMARINE_SHARD;
	}

	@Override
	public int getColour()
	{
		return 0x101A79;
	}

	@Override
	public void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack)
	{

	}
}
