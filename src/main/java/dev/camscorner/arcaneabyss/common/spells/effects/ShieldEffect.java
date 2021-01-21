package dev.camscorner.arcaneabyss.common.spells.effects;

import dev.camscorner.arcaneabyss.api.spells.SpellEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ShieldEffect extends SpellEffect
{
	public ShieldEffect(float costMultiplier)
	{
		super(costMultiplier, "a");
	}

	@Override
	public Item getItemCost()
	{
		return Items.SCUTE;
	}

	@Override
	public int getColour()
	{
		return 0x8A0A09;
	}

	@Override
	public void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack)
	{

	}
}
