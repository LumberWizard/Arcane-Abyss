package dev.camscorner.arcaneabyss.core.registry;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.common.entities.projectiles.FluxBlastEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;

public class ModEntities
{
	//----Entity Map----//
	public static final LinkedHashMap<EntityType, Identifier> ENTITIES = new LinkedHashMap<>();

	//-----Entities-----//
	public static final EntityType<FluxBlastEntity> FLUX_BLAST = create("flux_blast", FabricEntityTypeBuilder
			.<FluxBlastEntity>create(SpawnGroup.MISC, (type, world) -> new FluxBlastEntity(world))
			.dimensions(EntityDimensions.fixed(0.5F, 0.5F)).build());

	//-----Registry-----//
	public static void register()
	{
		ENTITIES.keySet().forEach(type -> Registry.register(Registry.ENTITY_TYPE, ENTITIES.get(type), type));
	}

	private static <T extends EntityType> T create(String name, T type)
	{
		ENTITIES.put(type, new Identifier(ArcaneAbyss.MOD_ID, name));
		return type;
	}
}
