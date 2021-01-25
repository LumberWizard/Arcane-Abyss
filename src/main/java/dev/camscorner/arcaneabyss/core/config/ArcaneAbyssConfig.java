package dev.camscorner.arcaneabyss.core.config;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

@Config(name = ArcaneAbyss.MOD_ID)
public class ArcaneAbyssConfig implements ConfigData
{
	public boolean enableRiftDistortionShader = true;
	public int maxNaturalRiftFlux = 300;
}
