package dev.camscorner.arcaneabyss.common.spells.effects;

import dev.camscorner.arcaneabyss.api.spells.SpellEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class TemporalEffect extends SpellEffect
{
	public TemporalEffect(float costMultiplier)
	{
		super(costMultiplier, "h");
	}

	@Override
	public Item getItemCost()
	{
		return Items.CLOCK;
	}

	@Override
	public int getColour()
	{
		return 0xE3A51E;
	}

	@Override
	public void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack)
	{

	}
}
