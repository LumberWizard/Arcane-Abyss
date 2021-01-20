package dev.camscorner.arcaneabyss.common.items;

import dev.camscorner.arcaneabyss.api.ArcaneAbyssApi;
import dev.camscorner.arcaneabyss.api.spells.SpellComponent;
import dev.camscorner.arcaneabyss.api.spells.SpellElement;
import dev.camscorner.arcaneabyss.api.spells.SpellModifier;
import dev.camscorner.arcaneabyss.api.spells.SpellShape;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class RunicStoneItem extends Item
{
	public RunicStoneItem(Item.Settings settings)
	{
		super(settings);
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
	{
		LiteralText text = new LiteralText("");
		SpellComponent component = ArcaneAbyssApi.COMPONENT.get(new Identifier(stack.getOrCreateTag().getString("Component")));

		if(component != null)
		{
			if(component instanceof SpellShape)
				text.append(((SpellShape) component).getShapeIcon());
			else if(component instanceof SpellElement)
				text.append(((SpellElement) component).getElementIcon());
			else
				text.append(((SpellModifier) component).getModifierIcon());
		}

		tooltip.add(text);
		tooltip.add(new LiteralText(""));
	}

	@Override
	public String getTranslationKey(ItemStack stack)
	{
		if(stack.getOrCreateTag().getString("Component") != null)
		{
			String id = stack.getOrCreateTag().getString("Component");
			return super.getTranslationKey() + "." + id.substring(id.indexOf(":") + 1);
		}
		else
		{
			return super.getTranslationKey();
		}
	}

	@Override
	public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks)
	{
		if(isIn(group))
		{
			ArcaneAbyssApi.COMPONENT.forEach(component ->
			{
				ItemStack stack = new ItemStack(this);
				stack.getOrCreateTag().putString("Component", Objects.requireNonNull(ArcaneAbyssApi.COMPONENT.getId(component)).toString());
				stacks.add(stack);
			});
		}
	}
}
