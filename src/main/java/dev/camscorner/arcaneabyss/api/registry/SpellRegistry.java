package dev.camscorner.arcaneabyss.api.registry;

import dev.camscorner.arcaneabyss.api.spells.SpellComponent;
import dev.camscorner.arcaneabyss.core.mixin.RegistryAccessor;
import dev.camscorner.arcaneabyss.core.registry.ModSpellComponents;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.RegistryKey;

public class SpellRegistry
{
	public static final RegistryKey<net.minecraft.util.registry.Registry<SpellComponent>> COMPONENT_KEY = RegistryAccessor.createRegistryKey("spell_component");
	public static final DefaultedRegistry<SpellComponent> COMPONENT = RegistryAccessor.create(COMPONENT_KEY, "beam_shape", () -> ModSpellComponents.BEAM_SHAPE);
}
