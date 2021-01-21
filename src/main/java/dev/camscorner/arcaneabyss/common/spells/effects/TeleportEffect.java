package dev.camscorner.arcaneabyss.common.spells.effects;

import dev.camscorner.arcaneabyss.api.spells.SpellEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class TeleportEffect extends SpellEffect
{
	public TeleportEffect(float costMultiplier)
	{
		super(costMultiplier, "b");
	}

	@Override
	public Item getItemCost()
	{
		return Items.ENDER_PEARL;
	}

	@Override
	public int getColour()
	{
		return 0xA40190;
	}

	@Override
	public void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack)
	{

	}
}
