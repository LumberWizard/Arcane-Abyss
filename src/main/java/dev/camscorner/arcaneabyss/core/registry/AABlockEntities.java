package dev.camscorner.arcaneabyss.core.registry;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.common.blocks.entities.AltarBlockEntity;
import dev.camscorner.arcaneabyss.common.blocks.entities.EntropicRiftBlockEntity;
import dev.camscorner.arcaneabyss.common.blocks.entities.InscriptionTableBlockEntity;
import dev.camscorner.arcaneabyss.common.blocks.entities.PedestalBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class AABlockEntities
{
	//-----Block Entity Type Map-----//
	private static final Map<BlockEntityType<?>, Identifier> BLOCK_ENTITY_TYPES = new LinkedHashMap<>();

	//-----Block Entity Types-----//
	public static final BlockEntityType<EntropicRiftBlockEntity> ENTROPIC_RIFT = create("entropic_rift", BlockEntityType.Builder.create(EntropicRiftBlockEntity::new, AABlocks.ENTROPIC_RIFT).build(null));
	public static final BlockEntityType<AltarBlockEntity> ALTAR = create("altar", BlockEntityType.Builder.create(AltarBlockEntity::new, AABlocks.ALTAR).build(null));
	public static final BlockEntityType<PedestalBlockEntity> PEDESTAL = create("pedestal", BlockEntityType.Builder.create(PedestalBlockEntity::new, AABlocks.PEDESTAL).build(null));
	public static final BlockEntityType<InscriptionTableBlockEntity> INSCRIPTION_TABLE = create("inscription_table", BlockEntityType.Builder.create(InscriptionTableBlockEntity::new, AABlocks.INSCRIPTION_TABLE).build(null));

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
