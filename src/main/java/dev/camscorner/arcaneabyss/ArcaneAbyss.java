package dev.camscorner.arcaneabyss;

import dev.camscorner.arcaneabyss.common.gui.InscriptionTableScreenHandler;
import dev.camscorner.arcaneabyss.common.network.packets.SetStaffItemMessage;
import dev.camscorner.arcaneabyss.common.world.feature.EntropicRiftFeature;
import dev.camscorner.arcaneabyss.core.config.ArcaneAbyssConfig;
import dev.camscorner.arcaneabyss.core.registry.*;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArcaneAbyss implements ModInitializer
{
	public static final ScreenHandlerType<InscriptionTableScreenHandler> INSCRIPTION_TABLE_SCREEN_HANDLER;
	public static final String MOD_ID = "arcaneabyss";
	public static final Logger LOGGER = LogManager.getLogger();
	public static ArcaneAbyssConfig config;

	private static final Feature<DefaultFeatureConfig> ENTROPIC_RIFT = new EntropicRiftFeature(DefaultFeatureConfig.CODEC);
	private static final ConfiguredFeature<?, ?> ENTROPIC_RIFT_CONFIGURED = ENTROPIC_RIFT.configure(FeatureConfig.DEFAULT)
			.decorate(Decorator.CHANCE.configure(new ChanceDecoratorConfig(100)));

	@Override
	public void onInitialize()
	{
		AutoConfig.register(ArcaneAbyssConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(ArcaneAbyssConfig.class).getConfig();

		ServerPlayNetworking.registerGlobalReceiver(SetStaffItemMessage.ID, SetStaffItemMessage::handle);

		AAShaders.register();
		AABlocks.register();
		AABlockEntities.register();
		AAItems.register();
		AAEntities.register();
		AAStatusEffects.register();
		AAParticleTypes.register();
		AARecipes.registerSerializers();
		AARecipes.registerTypes();
		AASpellComponents.register();
		AASoundEvents.register();

		Registry.register(Registry.FEATURE, new Identifier(MOD_ID, "entropic_rift"), ENTROPIC_RIFT);
		RegistryKey<ConfiguredFeature<?, ?>> entropicRift = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN,
				new Identifier(MOD_ID, "entropic_rift"));
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, entropicRift.getValue(), ENTROPIC_RIFT_CONFIGURED);
		BiomeModifications.addFeature(BiomeSelectors.all(), GenerationStep.Feature.UNDERGROUND_ORES, entropicRift);

		LOGGER.info("If you gaze long enough into an abyss, the abyss will gaze back into you...");
	}

	static
	{
		INSCRIPTION_TABLE_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MOD_ID, "inscription_table"),
				InscriptionTableScreenHandler::new);
	}
}
