package dev.camscorner.arcaneabyss.common.spells.elements;

import dev.camscorner.arcaneabyss.api.spells.SpellElement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class TeleportElement extends SpellElement
{
	public TeleportElement(float costMultiplier)
	{
		super(costMultiplier, "b");
	}

	@Override
	public Item getItemCost()
	{
		return Items.ENDER_PEARL;
	}

	@Override
	public void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack)
	{

	}
}
