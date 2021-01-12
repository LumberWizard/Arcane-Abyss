package dev.camscorner.arcaneabyss.core.mixin;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.JsonOps;
import dev.camscorner.arcaneabyss.api.recipes.codecs.TagCodec;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ShapedRecipe.class)
public class ShapedRecipeMixin
{
	@Inject(method = "getItemStack", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/util/JsonHelper;getInt(Lcom/google/gson/JsonObject;Ljava/lang/String;I)I", ordinal = 0), cancellable = true)
	private static void getItemStack(JsonObject json, CallbackInfoReturnable<ItemStack> info)
	{
		String string = JsonHelper.getString(json, "item");
		Item item = Registry.ITEM.getOrEmpty(new Identifier(string)).orElseThrow(() -> new JsonSyntaxException("Unknown item '" + string + "'"));
		int count = JsonHelper.getInt(json, "count", 1);
		ItemStack stack = new ItemStack(item, count);
		Optional<TagCodec> tagCodec = TagCodec.CODEC.parse(JsonOps.INSTANCE, json).result();
		tagCodec.ifPresent(value -> stack.getOrCreateTag().copyFrom(value.getTag()));

		info.setReturnValue(stack);
	}
}
