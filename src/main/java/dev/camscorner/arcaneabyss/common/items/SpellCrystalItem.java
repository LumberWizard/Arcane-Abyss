package dev.camscorner.arcaneabyss.common.items;

import dev.camscorner.arcaneabyss.api.spells.SpellComponent;
import dev.camscorner.arcaneabyss.api.spells.SpellElement;
import dev.camscorner.arcaneabyss.api.spells.SpellModifier;
import dev.camscorner.arcaneabyss.api.spells.SpellShape;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpellCrystalItem extends Item
{
	private SpellComponent[] spellComponents = new SpellComponent[6];

	public SpellCrystalItem(Settings settings)
	{
		super(settings);
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
	{
		LiteralText text = new LiteralText("");

		for(SpellComponent component : spellComponents)
		{
			if(component != null)
			{
				if(component instanceof SpellShape)
					text.append(((SpellShape) component).getShapeIcon());
				else if(component instanceof SpellElement)
					text.append(((SpellElement) component).getElementIcon());
				else
					text.append(((SpellModifier) component).getModifierIcon());
			}
		}

		tooltip.add(text);
	}
}
