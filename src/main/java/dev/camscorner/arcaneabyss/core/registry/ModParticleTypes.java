package dev.camscorner.arcaneabyss.core.registry;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModParticleTypes
{
	//-----Particle Type Map-----//
	private static final Map<ParticleType<?>, Identifier> PARTICLE_TYPES = new LinkedHashMap<>();

	//-----Particle Types-----//
	public static final ParticleType<DefaultParticleType> ENTROPIC_FLUX = create("entropic_flux", FabricParticleTypes.simple());

	//-----Registry-----//
	public static void register()
	{
		PARTICLE_TYPES.keySet().forEach(particleType -> Registry.register(Registry.PARTICLE_TYPE, PARTICLE_TYPES.get(particleType), particleType));
	}

	private static <T extends ParticleEffect> ParticleType<T> create(String name, ParticleType<T> particleEffect)
	{
		PARTICLE_TYPES.put(particleEffect, new Identifier(ArcaneAbyss.MOD_ID, name));
		return particleEffect;
	}
}
