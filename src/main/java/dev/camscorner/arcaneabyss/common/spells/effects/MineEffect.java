package dev.camscorner.arcaneabyss.common.spells.effects;

import dev.camscorner.arcaneabyss.api.spells.SpellEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class MineEffect extends SpellEffect
{
	public MineEffect(float costMultiplier)
	{
		super(costMultiplier, "8");
	}

	@Override
	public Item getItemCost()
	{
		return Items.DIAMOND;
	}

	@Override
	public int getColour()
	{
		return 0x0E395B;
	}

	@Override
	public void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack)
	{

	}
}
