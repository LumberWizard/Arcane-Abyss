package dev.camscorner.arcaneabyss.core.registry;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.common.effect.EntropicDecayStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;

public class AAStatusEffects
{
	//-----Status Effect Map-----//
	public static final LinkedHashMap<StatusEffect, Identifier> STATUS_EFFECTS = new LinkedHashMap<>();

	//-----Status Effects-----//
	public static final StatusEffect ENTROPIC_DECAY = create("entropic_decay", new EntropicDecayStatusEffect());

	//-----Registry-----//
	public static void register()
	{
		STATUS_EFFECTS.keySet().forEach(statusEffect -> Registry.register(Registry.STATUS_EFFECT, STATUS_EFFECTS.get(statusEffect), statusEffect));
	}

	private static <T extends StatusEffect> T create(String name, T statusEffect)
	{
		STATUS_EFFECTS.put(statusEffect, new Identifier(ArcaneAbyss.MOD_ID, name));
		return statusEffect;
	}
}
