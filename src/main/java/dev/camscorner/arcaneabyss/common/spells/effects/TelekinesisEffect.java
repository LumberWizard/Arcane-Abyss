package dev.camscorner.arcaneabyss.common.spells.effects;

import dev.camscorner.arcaneabyss.api.spells.SpellEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class TelekinesisEffect extends SpellEffect
{
	public TelekinesisEffect(float costMultiplier)
	{
		super(costMultiplier, "e");
	}

	@Override
	public Item getItemCost()
	{
		return Items.ENDER_EYE;
	}

	@Override
	public int getColour()
	{
		return 0xFEAA8E;
	}

	@Override
	public void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack)
	{

	}
}
