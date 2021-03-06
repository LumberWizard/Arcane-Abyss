package dev.camscorner.arcaneabyss.core.registry;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.api.recipes.AltarItemRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;

public class AARecipes
{
	//-----Serializer/Type Maps-----//
	public static final LinkedHashMap<RecipeSerializer, Identifier> SERIALIZERS = new LinkedHashMap<>();
	public static final LinkedHashMap<RecipeType, Identifier> TYPES = new LinkedHashMap<>();

	//-----Recipe Serializers-----//
	public static final RecipeSerializer<AltarItemRecipe> ALTAR_ITEM_SERIALIZER = createSerializer("altar_item", new AltarItemRecipe.Serializer());
	
	//-----Recipe Types-----//
	public static final RecipeType<AltarItemRecipe> ALTAR_ITEM_TYPE = createType("altar_item");

	//-----Registry-----//
	public static void registerSerializers()
	{
		SERIALIZERS.keySet().forEach(serializer -> Registry.register(Registry.RECIPE_SERIALIZER, SERIALIZERS.get(serializer), serializer));
	}

	public static void registerTypes()
	{
		TYPES.keySet().forEach(type -> Registry.register(Registry.RECIPE_TYPE, TYPES.get(type), type));
	}

	private static <S extends RecipeSerializer<T>, T extends Recipe<?>> S createSerializer(String id, S serializer)
	{
		SERIALIZERS.put(serializer, new Identifier(ArcaneAbyss.MOD_ID, id));
		return serializer;
	}

	private static <T extends Recipe<?>> RecipeType<T> createType(String id)
	{
		RecipeType<T> type = new RecipeType<T>()
		{
			public String toString()
			{
				return id;
			}
		};

		TYPES.put(type, new Identifier(ArcaneAbyss.MOD_ID, id));
		return type;
	}
}
