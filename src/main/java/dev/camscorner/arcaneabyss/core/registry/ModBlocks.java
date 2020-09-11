package dev.camscorner.arcaneabyss.core.registry;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.common.blocks.CorrodedBedrockBlock;
import dev.camscorner.arcaneabyss.common.blocks.VaciumCrystalBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
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
	public static final Block CORRODED_BEDROCK = create("corroded_bedrock", new CorrodedBedrockBlock());
	public static final Block VACIUM_CRYSTAL = create("vacium_crystal", new VaciumCrystalBlock());

	//-----Registry-----//
	public static void register()
	{
		BLOCKS.keySet().forEach(block -> Registry.register(Registry.BLOCK, BLOCKS.get(block), block));
		BLOCKS.keySet().forEach(block -> Registry.register(Registry.ITEM, BLOCKS.get(block), getItem(block)));
	}

	private static BlockItem getItem(Block block)
	{
		Item.Settings settings = new Item.Settings().group(ItemGroup.MISC);
		return new BlockItem(block, settings);
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
