package dev.camscorner.arcaneabyss.common.blocks;

import dev.camscorner.arcaneabyss.core.registry.ModBlocks;
import dev.camscorner.arcaneabyss.core.util.MathUtils;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Random;

public class VaciumCrystalBlock extends Block
{
	private static final IntProperty GROWTH_STAGE = IntProperty.of("age", 0, 7);

	public VaciumCrystalBlock()
	{
		super(FabricBlockSettings.of(Material.GLASS).ticksRandomly().noCollision().nonOpaque()
				.solidBlock(ModBlocks::never).suffocates(ModBlocks::never).blockVision(ModBlocks::never));
		this.setDefaultState(this.getDefaultState().with(GROWTH_STAGE, 0));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		builder.add(GROWTH_STAGE);
	}

	@Override
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player)
	{
		super.onBreak(world, pos, state, player);

		if(!world.isClient)
		{
			if(world.random.nextInt(5) == 0)
			{
				BlockPos blockPos = pos.down();
				BlockState blockState = world.getBlockState(blockPos);

				if(getAge(state) == getMaxAge())
				{
					if(blockState.getBlock() == Blocks.BEDROCK)
					{
						world.setBlockState(blockPos, ModBlocks.CORRODED_BEDROCK.getDefaultState(), 3);
					} else if(blockState.getBlock() == ModBlocks.CORRODED_BEDROCK)
					{
						CorrodedBedrockBlock block = (CorrodedBedrockBlock) blockState.getBlock();

						if(block.getCorrosion(blockState) < block.getMaxCorrosion())
						{
							world.setBlockState(blockPos, block.withCorrosion(block.getCorrosion(blockState) + 1), 3);
						} else
						{
							world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
						}
					}
				}
			}
		}
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos)
	{
		return canPlaceOn(world.getBlockState(pos.down()));
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
	{
		if(MathUtils.isWithinOrEqualTo(3, world.getBaseLightLevel(pos, 0), 6))
		{
			if(getAge(state) < getMaxAge() && pos.getY() <= 5)
			{
				if(random.nextInt(15 / Math.abs(MathHelper.clamp(pos.getY(), 1, 5) - 6)) == 0)
				{
					if(!world.getBlockState(pos.offset(Direction.fromHorizontal(random.nextInt(4)))).getMaterial().blocksLight())
					{
						world.setBlockState(pos, withAge(getAge(state) + 1), 2);
					}
				}
			}
		}
	}

	public int getAge(BlockState state)
	{
		return state.get(GROWTH_STAGE);
	}

	public int getMaxAge()
	{
		return 7;
	}

	public BlockState withAge(int age)
	{
		return this.getDefaultState().with(GROWTH_STAGE, age);
	}

	public boolean canPlaceOn(BlockState floor)
	{
		return floor.isOf(Blocks.BEDROCK) || floor.isOf(ModBlocks.CORRODED_BEDROCK);
	}
}
