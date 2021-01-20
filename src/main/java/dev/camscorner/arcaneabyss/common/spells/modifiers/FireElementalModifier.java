package dev.camscorner.arcaneabyss.common.spells.modifiers;

import dev.camscorner.arcaneabyss.api.spells.SpellModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class FireElementalModifier extends SpellModifier
{
	public FireElementalModifier(float costMultiplier, int maxLevel)
	{
		super(costMultiplier, maxLevel, "o");
	}

	@Override
	public Item getItemCost()
	{
		return Items.FIRE_CHARGE;
	}

	@Override
	public int getColour()
	{
		return 0xFE2224;
	}

	@Override
	public void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack)
	{

	}
}
