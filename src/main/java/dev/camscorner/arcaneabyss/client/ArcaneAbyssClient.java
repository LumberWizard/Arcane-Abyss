package dev.camscorner.arcaneabyss.client;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.api.ArcaneAbyssApi;
import dev.camscorner.arcaneabyss.client.gui.screen.InscriptionTableScreen;
import dev.camscorner.arcaneabyss.client.models.InfusedArmourModel;
import dev.camscorner.arcaneabyss.client.network.packets.CreateProjectileEntityMessage;
import dev.camscorner.arcaneabyss.client.network.packets.SyncBlockEntityMessage;
import dev.camscorner.arcaneabyss.client.particles.EntropicFluxParticle;
import dev.camscorner.arcaneabyss.client.renderers.blockentity.AltarBlockEntityRenderer;
import dev.camscorner.arcaneabyss.client.renderers.blockentity.PedestalBlockEntityRenderer;
import dev.camscorner.arcaneabyss.client.renderers.entity.FluxBlastEntityRenderer;
import dev.camscorner.arcaneabyss.core.registry.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderingRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.fabricmc.fabric.impl.client.particle.ParticleFactoryRegistryImpl;
import net.fabricmc.fabric.impl.client.rendering.ColorProviderRegistryImpl;
import net.fabricmc.fabric.impl.networking.ClientSidePacketRegistryImpl;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

import static dev.camscorner.arcaneabyss.core.registry.ModItems.RUNIC_STONE;

@Environment(EnvType.CLIENT)
public class ArcaneAbyssClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		//-----Packet Registry-----//
		ClientSidePacketRegistryImpl.INSTANCE.register(SyncBlockEntityMessage.ID, SyncBlockEntityMessage::handle);
		ClientSidePacketRegistryImpl.INSTANCE.register(CreateProjectileEntityMessage.ID, CreateProjectileEntityMessage::handle);

		//-----Predicate Registry-----//
		registerPredicates();

		//-----Particle Registry-----//
		ParticleFactoryRegistryImpl.INSTANCE.register(ModParticleTypes.ENTROPIC_FLUX, EntropicFluxParticle.Factory::new);

		//-----Custom Item Model Registry-----//
		ModelLoadingRegistry.INSTANCE.registerAppender((resourceManager, out) -> out.accept(new ModelIdentifier(new Identifier(ArcaneAbyss.MOD_ID, "infused_staff_in_hand"), "inventory")));
		ModelLoadingRegistry.INSTANCE.registerAppender((resourceManager, out) -> out.accept(new ModelIdentifier(new Identifier(ArcaneAbyss.MOD_ID, "fluxthrower_in_hand"), "inventory")));

		//-----Block Layers Registry-----//
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.INSCRIPTION_TABLE, ModBlocks.RELAY);

		//-----Block Entity Renderer Registry-----//
		BlockEntityRendererRegistry.INSTANCE.register(ModBlockEntities.ALTAR, AltarBlockEntityRenderer::new);
		BlockEntityRendererRegistry.INSTANCE.register(ModBlockEntities.PEDESTAL, PedestalBlockEntityRenderer::new);

		//-----Entity Renderer Registry-----//
		EntityRendererRegistry.INSTANCE.register(ModEntities.FLUX_BLAST, ((dispatcher, context) -> new FluxBlastEntityRenderer(dispatcher)));

		//-----Custom Armour Model Registry-----//
		ArmorRenderingRegistry.registerModel((entity, stack, slot, model) -> new InfusedArmourModel<>(slot), ModItems.INFUSED_HOOD, ModItems.INFUSED_ROBES, ModItems.INFUSED_GRIEVES);
		ArmorRenderingRegistry.registerTexture((entity, stack, slot, layer2, suffix, texture) -> new Identifier(ArcaneAbyss.MOD_ID, "textures/models/armour/infused_armour.png"), ModItems.INFUSED_HOOD, ModItems.INFUSED_ROBES, ModItems.INFUSED_GRIEVES);

		//-----Keybinding Registry-----//
		ModKeybinds.register();

		//-----Screen Registry-----//
		ScreenRegistry.register(ArcaneAbyss.INSCRIPTION_TABLE_SCREEN_HANDLER, InscriptionTableScreen::new);

		//-----Event Registry-----//
		ModEvents.clientEvents();

		//-----Colour Registry-----//
		ColorProviderRegistryImpl.ITEM.register(((stack, tintIndex) -> tintIndex == 0 ? 0xFFFFFF :
				ArcaneAbyssApi.COMPONENT.get(new Identifier(stack.getOrCreateTag().getString("Component"))).getColour()), RUNIC_STONE);
	}

	public void registerPredicates()
	{
		FabricModelPredicateProviderRegistry.register(new Identifier(ArcaneAbyss.MOD_ID, "complexity"), (stack, world, entity) -> stack.getOrCreateTag().getShort("Complexity"));

		FabricModelPredicateProviderRegistry.register(new Identifier(ArcaneAbyss.MOD_ID, "known"), (stack, world, entity) -> Boolean.compare(stack.getOrCreateTag().getBoolean("Known"), false));
	}
}
