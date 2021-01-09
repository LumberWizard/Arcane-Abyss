package dev.camscorner.arcaneabyss.common.blocks;

import dev.camscorner.arcaneabyss.common.blocks.entities.AltarBlockEntity;
import dev.camscorner.arcaneabyss.common.blocks.enums.AltarPart;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AltarBlock extends HorizontalFacingBlock implements BlockEntityProvider
{
	public static final EnumProperty<AltarPart> PART = EnumProperty.of("part", AltarPart.class);
	protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);
	protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);
	protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);
	protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);

	public AltarBlock(Settings settings)
	{
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(PART, AltarPart.LEFT));
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
	{
		Direction direction = getOppositePartDirection(state).getOpposite();

		switch(direction)
		{
			case NORTH:
				return NORTH_SHAPE;
			case SOUTH:
				return SOUTH_SHAPE;
			case WEST:
				return WEST_SHAPE;
			default:
				return EAST_SHAPE;
		}
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		builder.add(FACING, PART);
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack)
	{
		super.onPlaced(world, pos, state, placer, itemStack);

		if(!world.isClient)
		{
			BlockPos blockPos = pos.offset(state.get(FACING));
			world.setBlockState(blockPos, state.with(PART, AltarPart.LEFT), 3);
			world.updateNeighbors(pos, Blocks.AIR);
			state.updateNeighbors(world, pos, 3);
		}
	}

	@Override
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player)
	{
		if(!world.isClient && player.isCreative())
		{
			AltarPart altarPart = state.get(PART);

			if(altarPart == AltarPart.LEFT)
			{
				BlockPos blockPos = pos.offset(getDirectionTowardsOtherPart(altarPart, state.get(FACING)));
				BlockState blockState = world.getBlockState(blockPos);

				if(blockState.getBlock() == this && blockState.get(PART) == AltarPart.RIGHT)
				{
					world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 35);
					world.syncWorldEvent(player, 2001, blockPos, Block.getRawIdFromState(blockState));
				}
			}
		}

		super.onBreak(world, pos, state, player);
	}

	@Override
	@Nullable
	public BlockState getPlacementState(ItemPlacementContext ctx)
	{
		Direction direction = ctx.getPlayerFacing();
		BlockPos blockPos = ctx.getBlockPos();
		BlockPos blockPos2 = blockPos.offset(direction);
		return ctx.getWorld().getBlockState(blockPos2).canReplace(ctx) ? this.getDefaultState().with(FACING, direction) : null;
	}

	@Override
	public PistonBehavior getPistonBehavior(BlockState state)
	{
		return PistonBehavior.BLOCK;
	}

	private static Direction getDirectionTowardsOtherPart(AltarPart part, Direction direction)
	{
		return part == AltarPart.LEFT ? direction : direction.getOpposite();
	}

	public static Direction getOppositePartDirection(BlockState state)
	{
		Direction direction = state.get(FACING);
		return state.get(PART) == AltarPart.RIGHT ? direction.getOpposite() : direction;
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockView world)
	{
		return new AltarBlockEntity();
	}
}
