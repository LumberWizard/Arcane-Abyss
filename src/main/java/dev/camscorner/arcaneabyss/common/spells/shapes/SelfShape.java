package dev.camscorner.arcaneabyss.common.spells.shapes;

import dev.camscorner.arcaneabyss.api.spells.SpellShape;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class SelfShape extends SpellShape
{
	public SelfShape(float costMultiplier, boolean isInstant)
	{
		super(costMultiplier, isInstant, "6");
	}

	@Override
	public Item getItemCost()
	{
		return Items.CLAY_BALL;
	}

	@Override
	public int getColour()
	{
		return 0x169C9C;
	}

	@Override
	public void onRightClick(PlayerEntity caster, World world, Hand hand, ItemStack stack)
	{

	}
}
