package dev.camscorner.arcaneabyss.core.registry;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.common.blocks.entities.AltarBlockEntity;
import dev.camscorner.arcaneabyss.common.blocks.entities.PedestalBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModBlockEntities
{
	//-----Block Entity Type Map-----//
	private static final Map<BlockEntityType<?>, Identifier> BLOCK_ENTITY_TYPES = new LinkedHashMap<>();

	//-----Block Entity Types-----//
	public static final BlockEntityType<AltarBlockEntity> ALTAR = create("altar", BlockEntityType.Builder.create(AltarBlockEntity::new, ModBlocks.ALTAR).build(null));
	public static final BlockEntityType<PedestalBlockEntity> PEDESTAL = create("pedestal", BlockEntityType.Builder.create(PedestalBlockEntity::new, ModBlocks.PEDESTAL).build(null));

	//-----Registry-----//
	public static void register()
	{
		BLOCK_ENTITY_TYPES.keySet().forEach(blockEntityType -> Registry.register(Registry.BLOCK_ENTITY_TYPE, BLOCK_ENTITY_TYPES.get(blockEntityType), blockEntityType));
	}

	private static <T extends BlockEntity> BlockEntityType<T> create(String name, BlockEntityType<T> type)
	{
		BLOCK_ENTITY_TYPES.put(type, new Identifier(ArcaneAbyss.MOD_ID, name));
		return type;
	}
}
