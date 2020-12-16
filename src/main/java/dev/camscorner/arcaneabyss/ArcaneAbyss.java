package dev.camscorner.arcaneabyss;

import dev.camscorner.arcaneabyss.core.registry.*;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArcaneAbyss implements ModInitializer
{
	public static final String MOD_ID = "arcaneabyss";
	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void onInitialize()
	{
		ModBlocks.register();
		ModItems.register();
		ModEntities.register();
		ModRecipes.registerSerializers();
		ModRecipes.registerTypes();
		ModSpellComponents.register();

		LOGGER.info("If you gaze long enough into an abyss, the abyss will gaze back into you...");
	}
}
