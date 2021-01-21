package dev.camscorner.arcaneabyss.common.spells.effects;

import dev.camscorner.arcaneabyss.api.spells.SpellEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class HealEffect extends SpellEffect
{
	public HealEffect(float costMultiplier)
	{
		super(costMultiplier, "9");
	}

	@Override
	public Item getItemCost()
	{
		return Items.GOLDEN_APPLE;
	}

	@Override
	public int getColour()
	{
		return 0x5E7C16;
	}

	@Override
	public void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack)
	{

	}
}
