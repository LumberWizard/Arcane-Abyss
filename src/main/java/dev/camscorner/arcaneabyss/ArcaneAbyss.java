package dev.camscorner.arcaneabyss;

import dev.camscorner.arcaneabyss.core.registry.ModBlocks;
import dev.camscorner.arcaneabyss.core.registry.ModItems;
import net.fabricmc.api.ModInitializer;

public class ArcaneAbyss implements ModInitializer
{
	public static final String MOD_ID = "arcaneabyss";

	@Override
	public void onInitialize()
	{
		ModItems.register();
		ModBlocks.register();
		System.out.println("If you gaze long enough into an abyss, the abyss will gaze back into you...");
	}
}
