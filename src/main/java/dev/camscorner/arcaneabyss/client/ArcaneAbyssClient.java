package dev.camscorner.arcaneabyss.client;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.client.models.InfusedArmourModel;
import dev.camscorner.arcaneabyss.core.registry.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderingRegistry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ArcaneAbyssClient implements ClientModInitializer
{
	private Identifier armourTex = new Identifier(ArcaneAbyss.MOD_ID, "textures/models/armour/infused_armour.png");
	@Override
	public void onInitializeClient()
	{
		ArmorRenderingRegistry.registerModel((entity, stack, slot, model) -> new InfusedArmourModel(EquipmentSlot.HEAD), ModItems.INFUSED_HOOD);
		ArmorRenderingRegistry.registerTexture((entity, stack, slot, layer2, suffix, texture) -> armourTex, ModItems.INFUSED_HOOD);
		ArmorRenderingRegistry.registerModel((entity, stack, slot, model) -> new InfusedArmourModel(EquipmentSlot.CHEST), ModItems.INFUSED_ROBES);
		ArmorRenderingRegistry.registerTexture((entity, stack, slot, layer2, suffix, texture) -> armourTex, ModItems.INFUSED_ROBES);
		ArmorRenderingRegistry.registerModel((entity, stack, slot, model) -> new InfusedArmourModel(EquipmentSlot.LEGS), ModItems.INFUSED_LEGS);
		ArmorRenderingRegistry.registerTexture((entity, stack, slot, layer2, suffix, texture) -> armourTex, ModItems.INFUSED_LEGS);
	}
}
