package dev.camscorner.arcaneabyss.api.spells;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public abstract class SpellShape implements SpellComponent
{
	private float costMultiplier;
	private boolean isInstant;
	private String componentIcon;

	public SpellShape(float costMultiplier, boolean isInstant, String componentIcon)
	{
		this.costMultiplier = costMultiplier;
		this.isInstant = isInstant;
		this.componentIcon = componentIcon;
	}

	public abstract void onRightClick(PlayerEntity caster, World world, Hand hand, ItemStack stack);

	public float getCostMultiplier()
	{
		return this.costMultiplier;
	}

	public boolean isInstant()
	{
		return this.isInstant;
	}

	public MutableText getShapeIcon()
	{
		return new LiteralText(componentIcon).fillStyle(Style.EMPTY.withFont(DEFAULT_FONT));
	}
}
