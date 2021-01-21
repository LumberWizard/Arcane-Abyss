package dev.camscorner.arcaneabyss.common.spells.effects;

import dev.camscorner.arcaneabyss.api.spells.SpellEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ConjureElementalEffect extends SpellEffect
{
	public ConjureElementalEffect(float costMultiplier)
	{
		super(costMultiplier, "d");
	}

	@Override
	public Item getItemCost()
	{
		return Items.SOUL_SOIL;
	}

	@Override
	public int getColour()
	{
		return 0x8932B8;
	}

	@Override
	public void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack)
	{

	}
}
