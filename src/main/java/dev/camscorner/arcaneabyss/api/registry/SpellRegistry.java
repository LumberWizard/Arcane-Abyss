package dev.camscorner.arcaneabyss.api.registry;

import com.mojang.serialization.Lifecycle;
import dev.camscorner.arcaneabyss.api.spells.SpellComponent;
import dev.camscorner.arcaneabyss.core.mixin.RegistryAccessor;
import dev.camscorner.arcaneabyss.core.registry.ModSpellComponents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import java.util.function.Supplier;

public class SpellRegistry
{
	public static final RegistryKey<net.minecraft.util.registry.Registry<SpellComponent>> COMPONENT_KEY = createRegistryKey("spell_component");
	public static final DefaultedRegistry<SpellComponent> COMPONENT = create(COMPONENT_KEY, "beam_shape", () -> ModSpellComponents.BEAM_SHAPE);

	private static <T> RegistryKey<net.minecraft.util.registry.Registry<T>> createRegistryKey(String registryId)
	{
		return RegistryKey.ofRegistry(new Identifier(registryId));
	}

	private static <T, R extends MutableRegistry<T>> R create(RegistryKey<? extends Registry<T>> registryKey, String defaultId, Supplier<T> defaultEntry)
	{
		Identifier identifier = registryKey.getValue();
		DefaultedRegistry registry = new DefaultedRegistry(defaultId, registryKey, Lifecycle.experimental());
		((RegistryAccessor) registry).getDefaultEntries().put(identifier, defaultEntry);
		MutableRegistry mutableRegistry = ((RegistryAccessor) registry).getRoot();
		return (R) mutableRegistry.add(registryKey, registry, Lifecycle.experimental());
	}
}
