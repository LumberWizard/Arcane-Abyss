package dev.camscorner.arcaneabyss.core.mixin;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import java.util.function.Supplier;

@Mixin(Registry.class)
public interface RegistryAccessor
{
	@Accessor("DEFAULT_ENTRIES")
	Map<Identifier, Supplier<?>> getDefaultEntries();

	@Accessor("ROOT")
	MutableRegistry<MutableRegistry<?>> getRoot();
}
