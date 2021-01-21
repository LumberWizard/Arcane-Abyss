package dev.camscorner.arcaneabyss.common.items;

import dev.camscorner.arcaneabyss.api.ArcaneAbyssApi;
import dev.camscorner.arcaneabyss.api.spells.SpellComponent;
import dev.camscorner.arcaneabyss.api.spells.SpellElement;
import dev.camscorner.arcaneabyss.api.spells.SpellModifier;
import dev.camscorner.arcaneabyss.api.spells.SpellShape;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpellCrystalItem extends Item
{
	public SpellComponent[] spellComponents = new SpellComponent[6];

	public SpellCrystalItem(Settings settings)
	{
		super(settings);
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
	{
		for(int i = 0; i < spellComponents.length; i++)
		{
			spellComponents[i] = ArcaneAbyssApi.COMPONENT.get(new Identifier(stack.getOrCreateTag().getString("Component_" + i)));
		}

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

		if(!text.equals(new LiteralText("")))
		{
			tooltip.add(text);
			tooltip.add(new LiteralText(""));
		}
	}

	@Override
	public boolean hasGlint(ItemStack stack)
	{
		return stack.getOrCreateTag().contains("Component_0");
	}
}
