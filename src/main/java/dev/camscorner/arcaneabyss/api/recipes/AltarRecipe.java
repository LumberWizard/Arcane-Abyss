package dev.camscorner.arcaneabyss.api.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.JsonOps;
import dev.camscorner.arcaneabyss.api.recipes.codecs.Result;
import dev.camscorner.arcaneabyss.core.registry.ModRecipes;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Optional;

public class AltarRecipe implements Recipe<Inventory>
{
	private final Identifier id;
	private final int flux;
	private final Ingredient base;
	private final DefaultedList<Ingredient> additions;
	private final ItemStack result;

	public AltarRecipe(Identifier id, int flux, Ingredient base, DefaultedList<Ingredient> additions, ItemStack result)
	{
		this.id = id;
		this.flux = flux;
		this.base = base;
		this.additions = additions;
		this.result = result;
	}

	@Override
	public boolean matches(Inventory inv, World world)
	{
		RecipeFinder recipeFinder = new RecipeFinder();
		int i = 0;

		for(int j = 0; j < inv.size(); ++j)
		{
			ItemStack itemStack = inv.getStack(j);

			if(!itemStack.isEmpty())
			{
				++i;
				recipeFinder.method_20478(itemStack, 1);
			}
		}

		return i == this.additions.size() && recipeFinder.findRecipe(this, null);
	}

	@Override
	public ItemStack craft(Inventory inv)
	{
		ItemStack stack = this.result.copy();
		CompoundTag tag = inv.getStack(0).getOrCreateTag();

		if(tag != null && stack.hasTag())
		{
			stack.getOrCreateTag().copyFrom(tag);
		}

		return stack;
	}

	@Override
	public boolean fits(int width, int height)
	{
		return width * height >= 6;
	}

	@Override
	public ItemStack getOutput()
	{
		return this.result;
	}

	@Override
	public Identifier getId()
	{
		return this.id;
	}

	@Override
	public RecipeSerializer<?> getSerializer()
	{
		return ModRecipes.ALTAR_SERIALIZER;
	}

	@Override
	public RecipeType<?> getType()
	{
		return ModRecipes.ALTAR_TYPE;
	}

	public static ItemStack getItemStack(JsonObject json)
	{
		String string = JsonHelper.getString(json, "item");
		Item item = Registry.ITEM.getOrEmpty(new Identifier(string)).orElseThrow(() ->
				new JsonSyntaxException("Unknown item: '" + string + "'"));

		if(json.has("data"))
		{
			throw new JsonParseException("Disallowed data tag found!");
		}
		else
		{
			int count = JsonHelper.getInt(json, "count", 1);
			ItemStack stack = new ItemStack(item, count);

			Optional<Result> result = Result.CODEC.parse(JsonOps.INSTANCE, json).resultOrPartial(System.out::println);
			result.ifPresent(value -> stack.getOrCreateTag().copyFrom(value.getTag()));

			return stack;
		}
	}

	public static class Serializer implements RecipeSerializer<AltarRecipe>
	{
		@Override
		public AltarRecipe read(Identifier id, JsonObject json)
		{
			DefaultedList<Ingredient> additions = getAdditions(JsonHelper.getArray(json, "aux_ingredients"));

			if(additions.size() > 6)
			{
				throw new JsonParseException("Too many ingredients for altar recipe");
			}
			else
			{
				int flux = JsonHelper.getInt(json, "entropic_flux");
				ItemStack result = AltarRecipe.getItemStack(JsonHelper.getObject(json, "result"));
				Ingredient base = Ingredient.fromJson(JsonHelper.getObject(json, "base_ingredient"));

				return new AltarRecipe(id, flux, base, additions, result);
			}
		}

		@Override
		public AltarRecipe read(Identifier id, PacketByteBuf buf)
		{
			int flux = buf.readInt();
			ItemStack result = buf.readItemStack();
			Ingredient base = Ingredient.fromPacket(buf);

			int i = buf.readVarInt();
			DefaultedList<Ingredient> additions = DefaultedList.ofSize(i, Ingredient.EMPTY);

			for(int j = 0; j < additions.size(); ++j)
			{
				additions.set(j, Ingredient.fromPacket(buf));
			}

			return new AltarRecipe(id, flux, base, additions, result);
		}

		@Override
		public void write(PacketByteBuf buf, AltarRecipe recipe)
		{
			buf.writeInt(recipe.flux);
			buf.writeItemStack(recipe.result);
			recipe.base.write(buf);

			buf.writeVarInt(recipe.additions.size());

			for(int j = 0; j < recipe.additions.size(); ++j)
			{
				Ingredient ingredient = recipe.additions.get(j);
				ingredient.write(buf);
			}
		}

		private static DefaultedList<Ingredient> getAdditions(JsonArray json)
		{
			DefaultedList<Ingredient> list = DefaultedList.of();

			for(int i = 0; i < json.size(); ++i)
			{
				Ingredient ingredient = Ingredient.fromJson(json.get(i));

				if(!ingredient.isEmpty())
				{
					list.add(ingredient);
				}
			}

			return list;
		}
	}
}
