package dev.camscorner.arcaneabyss.client;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.client.models.InfusedArmourModel;
import dev.camscorner.arcaneabyss.core.registry.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderingRegistry;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ArcaneAbyssClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		ModelLoadingRegistry.INSTANCE.registerAppender((resourceManager, out) -> out.accept(new ModelIdentifier(new Identifier(ArcaneAbyss.MOD_ID, "infused_staff_in_hand"), "inventory")));

		ArmorRenderingRegistry.registerModel((entity, stack, slot, model) -> new InfusedArmourModel(slot), ModItems.INFUSED_HOOD, ModItems.INFUSED_ROBES, ModItems.INFUSED_GRIEVES);
		ArmorRenderingRegistry.registerTexture((entity, stack, slot, layer2, suffix, texture) -> new Identifier(ArcaneAbyss.MOD_ID, "textures/models/armour/infused_armour.png"), ModItems.INFUSED_HOOD, ModItems.INFUSED_ROBES, ModItems.INFUSED_GRIEVES);
	}
}
