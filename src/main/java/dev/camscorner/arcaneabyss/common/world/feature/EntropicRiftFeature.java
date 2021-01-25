package dev.camscorner.arcaneabyss.common.world.feature;

import com.mojang.serialization.Codec;
import dev.camscorner.arcaneabyss.core.registry.AABlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class EntropicRiftFeature extends Feature<DefaultFeatureConfig>
{
	public EntropicRiftFeature(Codec<DefaultFeatureConfig> config)
	{
		super(config);
	}

	@Override
	public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config)
	{
		BlockPos blockPos = pos.down(pos.getY() - 5);
		world.setBlockState(blockPos.up(world.getRandom().nextInt(6)), AABlocks.ENTROPIC_RIFT.getDefaultState(), 3);

		return true;
	}
}
