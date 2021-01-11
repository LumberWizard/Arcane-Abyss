package dev.camscorner.arcaneabyss.client;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.client.models.InfusedArmourModel;
import dev.camscorner.arcaneabyss.client.network.packets.SyncBlockEntityMessage;
import dev.camscorner.arcaneabyss.client.renderers.blockentity.PedestalBlockEntityRenderer;
import dev.camscorner.arcaneabyss.client.renderers.entity.FluxBlastEntityRenderer;
import dev.camscorner.arcaneabyss.core.registry.ModBlockEntities;
import dev.camscorner.arcaneabyss.core.registry.ModEntities;
import dev.camscorner.arcaneabyss.core.registry.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderingRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ArcaneAbyssClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		ClientSidePacketRegistry.INSTANCE.register(SyncBlockEntityMessage.ID, SyncBlockEntityMessage::handle);

		ModelLoadingRegistry.INSTANCE.registerAppender((resourceManager, out) -> out.accept(new ModelIdentifier(new Identifier(ArcaneAbyss.MOD_ID, "infused_staff_in_hand"), "inventory")));

		ArmorRenderingRegistry.registerModel((entity, stack, slot, model) -> new InfusedArmourModel<>(slot), ModItems.INFUSED_HOOD, ModItems.INFUSED_ROBES, ModItems.INFUSED_GRIEVES);
		ArmorRenderingRegistry.registerTexture((entity, stack, slot, layer2, suffix, texture) -> new Identifier(ArcaneAbyss.MOD_ID, "textures/models/armour/infused_armour.png"), ModItems.INFUSED_HOOD, ModItems.INFUSED_ROBES, ModItems.INFUSED_GRIEVES);

		EntityRendererRegistry.INSTANCE.register(ModEntities.FLUX_BLAST, ((dispatcher, context) -> new FluxBlastEntityRenderer(dispatcher)));

		BlockEntityRendererRegistry.INSTANCE.register(ModBlockEntities.PEDESTAL, PedestalBlockEntityRenderer::new);
	}
}
