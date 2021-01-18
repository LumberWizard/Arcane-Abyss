package dev.camscorner.arcaneabyss.api.spells;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public abstract class SpellModifier implements SpellComponent
{
	private float costMultiplier;
	private int maxLevel;
	private String componentIcon;

	public SpellModifier(float costMultiplier, int maxLevel, String componentIcon)
	{
		this.costMultiplier = costMultiplier;
		this.maxLevel = maxLevel;
		this.componentIcon = componentIcon;
	}

	public abstract void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack);

	public float getCostMultiplier()
	{
		return this.costMultiplier * maxLevel;
	}

	public int getMaxLevel()
	{
		return this.maxLevel;
	}

	public MutableText getModifierIcon()
	{
		return new LiteralText(componentIcon).fillStyle(Style.EMPTY.withFont(DEFAULT_FONT));
	}
}
