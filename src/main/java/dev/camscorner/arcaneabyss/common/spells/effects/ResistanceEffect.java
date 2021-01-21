package dev.camscorner.arcaneabyss.common.spells.effects;

import dev.camscorner.arcaneabyss.api.spells.SpellEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ResistanceEffect extends SpellEffect
{
	public ResistanceEffect(float costMultiplier)
	{
		super(costMultiplier, "f");
	}

	@Override
	public Item getItemCost()
	{
		return Items.SHULKER_SHELL;
	}

	@Override
	public int getColour()
	{
		return 0x05F3CF;
	}

	@Override
	public void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack)
	{

	}
}
