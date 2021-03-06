package dev.camscorner.arcaneabyss.common.spells.modifiers;

import dev.camscorner.arcaneabyss.api.spells.SpellModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class DurationModifier extends SpellModifier
{
	public DurationModifier(float costMultiplier, int maxLevel)
	{
		super(costMultiplier, maxLevel, "j");
	}

	@Override
	public Item getItemCost()
	{
		return Items.REDSTONE;
	}

	@Override
	public int getColour()
	{
		return 0x2C0F7C;
	}

	@Override
	public void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack)
	{

	}
}
