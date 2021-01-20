package dev.camscorner.arcaneabyss.common.spells.elements;

import dev.camscorner.arcaneabyss.api.spells.SpellElement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class DamageElement extends SpellElement
{
	public DamageElement(float costMultiplier)
	{
		super(costMultiplier, "7");
	}

	@Override
	public Item getItemCost()
	{
		return Items.IRON_AXE;
	}

	@Override
	public int getColour()
	{
		return 0x3C44AA;
	}

	@Override
	public void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack)
	{

	}
}
