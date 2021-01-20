package dev.camscorner.arcaneabyss.common.spells.elements;

import dev.camscorner.arcaneabyss.api.spells.SpellElement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class SwapElement extends SpellElement
{
	public SwapElement(float costMultiplier)
	{
		super(costMultiplier, "c");
	}

	@Override
	public Item getItemCost()
	{
		return Items.CHORUS_FLOWER;
	}

	@Override
	public int getColour()
	{
		return 0xFF974E;
	}

	@Override
	public void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack)
	{

	}
}
