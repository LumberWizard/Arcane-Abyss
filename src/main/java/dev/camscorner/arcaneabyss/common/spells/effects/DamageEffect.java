package dev.camscorner.arcaneabyss.common.spells.effects;

import dev.camscorner.arcaneabyss.api.spells.SpellEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class DamageEffect extends SpellEffect
{
	public DamageEffect(float costMultiplier)
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
