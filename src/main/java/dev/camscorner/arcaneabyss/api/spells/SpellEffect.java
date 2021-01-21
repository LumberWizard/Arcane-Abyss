package dev.camscorner.arcaneabyss.api.spells;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public abstract class SpellEffect implements SpellComponent
{
	private float costMultiplier;
	private String componentIcon;

	public SpellEffect(float costMultiplier, String componentIcon)
	{
		this.costMultiplier = costMultiplier;
		this.componentIcon = componentIcon;
	}

	public abstract void onCast(PlayerEntity caster, World world, Hand hand, ItemStack stack);

	public float getCostMultiplier()
	{
		return this.costMultiplier;
	}

	public MutableText getElementIcon()
	{
		return new LiteralText(componentIcon).fillStyle(Style.EMPTY.withFont(DEFAULT_FONT));
	}
}
