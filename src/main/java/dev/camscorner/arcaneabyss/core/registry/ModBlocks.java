package dev.camscorner.arcaneabyss.core.registry;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.api.ArcaneAbyssApi;
import dev.camscorner.arcaneabyss.common.blocks.AltarBlock;
import dev.camscorner.arcaneabyss.common.blocks.PedestalBlock;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;

import java.util.LinkedHashMap;

public class ModBlocks
{
	//-----Block Map-----//
	public static final LinkedHashMap<Block, Identifier> BLOCKS = new LinkedHashMap<>();

	//-----Blocks-----//
	public static final Block ENTROPIC_STONE = create("entropic_stone", new Block(AbstractBlock.Settings.of(Material.STONE,
			MaterialColor.GRAY).requiresTool().strength(1.5F, 6.0F)));
	public static final Block ENTROPIC_STONE_BRICKS = create("entropic_stone_bricks", new Block(AbstractBlock.Settings.of(Material.STONE,
			MaterialColor.GRAY).requiresTool().strength(1.5F, 6.0F)));
	public static final Block ENTROPIC_LOG = create("entropic_log", new PillarBlock(AbstractBlock.Settings.of(Material.WOOD,
			MaterialColor.PURPLE).strength(2.0F).sounds(BlockSoundGroup.WOOD)));
	public static final Block ENTROPIC_PLANKS = create("entropic_planks", new Block(AbstractBlock.Settings.of(Material.WOOD,
			MaterialColor.GRAY).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
	public static final Block ALTAR = create("altar", new AltarBlock(AbstractBlock.Settings.of(Material.STONE,
			MaterialColor.GRAY).requiresTool().strength(1.5F, 6.0F).luminance((state) -> 15)));
	public static final Block PEDESTAL = create("pedestal", new PedestalBlock(AbstractBlock.Settings.of(Material.STONE,
			MaterialColor.GRAY).requiresTool().strength(1.5F, 6.0F)));

	//-----Registry-----//
	public static void register()
	{
		BLOCKS.keySet().forEach(block -> Registry.register(Registry.BLOCK, BLOCKS.get(block), block));
		BLOCKS.keySet().forEach(block -> Registry.register(Registry.ITEM, BLOCKS.get(block), getItem(block)));

		FlammableBlockRegistry flammableRegistry = FlammableBlockRegistry.getDefaultInstance();
		flammableRegistry.add(ENTROPIC_PLANKS, 5, 20);
	}

	private static BlockItem getItem(Block block)
	{
		return new BlockItem(block, new Item.Settings().group(ArcaneAbyssApi.ITEM_GROUP));
	}

	private static <T extends Block> T create(String name, T block)
	{
		BLOCKS.put(block, new Identifier(ArcaneAbyss.MOD_ID, name));
		return block;
	}

	//-----Predicates-----//
	public static boolean never(BlockState state, BlockView world, BlockPos pos)
	{
		return false;
	}
}
