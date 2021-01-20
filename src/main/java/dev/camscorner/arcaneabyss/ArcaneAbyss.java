package dev.camscorner.arcaneabyss;

import dev.camscorner.arcaneabyss.common.gui.InscriptionTableScreenHandler;
import dev.camscorner.arcaneabyss.core.config.ArcaneAbyssConfig;
import dev.camscorner.arcaneabyss.core.registry.*;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArcaneAbyss implements ModInitializer
{
	public static final ScreenHandlerType<InscriptionTableScreenHandler> INSCRIPTION_TABLE_SCREEN_HANDLER;
	public static final String MOD_ID = "arcaneabyss";
	public static final Logger LOGGER = LogManager.getLogger();
	public static ArcaneAbyssConfig config;

	@Override
	public void onInitialize()
	{
		AutoConfig.register(ArcaneAbyssConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(ArcaneAbyssConfig.class).getConfig();

		ModShaders.register();
		ModBlocks.register();
		ModBlockEntities.register();
		ModItems.register();
		ModEntities.register();
		ModStatusEffects.register();
		ModParticleTypes.register();
		ModRecipes.registerSerializers();
		ModRecipes.registerTypes();
		ModSpellComponents.register();

		LOGGER.info("If you gaze long enough into an abyss, the abyss will gaze back into you...");
	}

	static
	{
		INSCRIPTION_TABLE_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MOD_ID, "inscription_table"),
				InscriptionTableScreenHandler::new);
	}
}
