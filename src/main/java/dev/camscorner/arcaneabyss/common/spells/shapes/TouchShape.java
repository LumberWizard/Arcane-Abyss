package dev.camscorner.arcaneabyss.common.spells.shapes;

import dev.camscorner.arcaneabyss.api.spells.SpellShape;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class TouchShape extends SpellShape
{
	public TouchShape(float costMultiplier, boolean isInstant)
	{
		super(costMultiplier, isInstant, "2");
	}

	@Override
	public Item getItemCost()
	{
		return Items.HONEY_BOTTLE;
	}

	@Override
	public int getColour()
	{
		return 0x3AB3DA;
	}

	@Override
	public void onRightClick(PlayerEntity caster, World world, Hand hand, ItemStack stack)
	{

	}
}
