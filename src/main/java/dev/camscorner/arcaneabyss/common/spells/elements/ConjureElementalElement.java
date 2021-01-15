package dev.camscorner.arcaneabyss.common.spells.elements;

import dev.camscorner.arcaneabyss.api.spells.SpellElement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ConjureElementalElement extends SpellElement
{
	public ConjureElementalElement(float costMultiplier)
	{
		super(costMultiplier);
	}

	@Override
	public Item getItemCost()
	{
		return Items.SOUL_SOIL;
	}

	@Override
	public void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack)
	{

	}
}
