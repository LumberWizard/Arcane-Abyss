package dev.camscorner.arcaneabyss.common.spells.elements;

import dev.camscorner.arcaneabyss.api.spells.SpellElement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class TelekinesisElement extends SpellElement
{
	public TelekinesisElement(float costMultiplier)
	{
		super(costMultiplier, "e");
	}

	@Override
	public Item getItemCost()
	{
		return Items.ENDER_EYE;
	}

	@Override
	public void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack)
	{

	}
}
