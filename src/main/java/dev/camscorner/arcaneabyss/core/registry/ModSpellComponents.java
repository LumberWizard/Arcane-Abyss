package dev.camscorner.arcaneabyss.core.registry;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.api.registry.SpellRegistry;
import dev.camscorner.arcaneabyss.api.spells.SpellComponent;
import dev.camscorner.arcaneabyss.common.spells.shapes.BeamShape;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;

public class ModSpellComponents
{
	//----Spell Component Map----//
	public static final LinkedHashMap<SpellComponent, Identifier> COMPONENTS = new LinkedHashMap<>();

	//-----Spell Components-----//
	public static final SpellComponent BEAM_SHAPE = create("beam_shape", new BeamShape(1.5, false));

	//-----Registry-----//
	public static void register()
	{
		COMPONENTS.keySet().forEach(item -> Registry.register(SpellRegistry.COMPONENT, COMPONENTS.get(item), item));
	}

	private static <T extends SpellComponent> T create(String name, T spellComponent)
	{
		COMPONENTS.put(spellComponent, new Identifier(ArcaneAbyss.MOD_ID, name));
		return spellComponent;
	}
}
