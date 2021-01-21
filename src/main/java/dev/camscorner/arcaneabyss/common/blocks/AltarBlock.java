package dev.camscorner.arcaneabyss.common.blocks;

import dev.camscorner.arcaneabyss.common.network.packets.SyncBlockEntityMessage;
import dev.camscorner.arcaneabyss.common.blocks.entities.AltarBlockEntity;
import dev.camscorner.arcaneabyss.common.blocks.enums.AltarPart;
import dev.camscorner.arcaneabyss.common.items.StaffItem;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class AltarBlock extends HorizontalFacingBlock implements BlockEntityProvider
{
	public static final EnumProperty<AltarPart> PART = EnumProperty.of("part", AltarPart.class);
	protected static final VoxelShape NORTH_SHAPE = VoxelShapes.fullCube();
	protected static final VoxelShape SOUTH_SHAPE = VoxelShapes.fullCube();
	protected static final VoxelShape WEST_SHAPE = VoxelShapes.fullCube();
	protected static final VoxelShape EAST_SHAPE = VoxelShapes.fullCube();

	public AltarBlock(Settings settings)
	{
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(PART, AltarPart.LEFT));
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
	{
		boolean client = world.isClient;
		ItemStack stack = player.getStackInHand(hand);
		AltarBlockEntity blockEntity = state.get(PART) == AltarPart.LEFT ? (AltarBlockEntity) world.getBlockEntity(pos) :
				(AltarBlockEntity) world.getBlockEntity(pos.offset(getOppositePartDirection(state)));

		if(!client)
		{
			if(!stack.isEmpty())
			{
				Item item = stack.getItem();

				if(!(item instanceof StaffItem) && item != blockEntity.getStack(0).getItem())
				{
					ItemScatterer.spawn(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, blockEntity.removeStack(0, 1));
					blockEntity.setStack(0, stack.split(1));
					blockEntity.sync();
				}
			}
			else
			{
				ItemScatterer.spawn(world, pos.add(0, 1, 0), blockEntity);
				blockEntity.sync();
				PlayerLookup.tracking(blockEntity).forEach(playerEntity -> SyncBlockEntityMessage.send(player, blockEntity));
			}
		}

		return ActionResult.success(client);
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
	{
		Direction direction = getOppositePartDirection(state).getOpposite();
		AltarPart part = state.get(PART);

		if(part == AltarPart.LEFT)
		{
			switch(direction)
			{
				case NORTH:
					// Left Side
					spawnParticle(world, pos, 0.05, 1.6, 0.225);
					spawnParticle(world, pos, 0.35, 1.7, 0.025);

					// Right Side
					spawnParticle(world, pos, 1.95, 1.6, 0.225);
					spawnParticle(world, pos, 1.65, 1.7, 0.025);
					break;
				case EAST:
					// Left Side
					spawnParticle(world, pos, 0.755, 1.6, 0.05);
					spawnParticle(world, pos, 0.975, 1.7, 0.35);

					// Right Side
					spawnParticle(world, pos, 0.775, 1.6, 1.95);
					spawnParticle(world, pos, 0.975, 1.7, 1.65);
					break;
				case SOUTH:
					// Left Side
					spawnParticle(world, pos, 0.95, 1.6, 0.775);
					spawnParticle(world, pos, 0.65, 1.7, 0.975);

					// Right Side
					spawnParticle(world, pos, -0.95, 1.6, 0.775);
					spawnParticle(world, pos, -0.65, 1.7, 0.975);
					break;
				case WEST:
					// Left Side
					spawnParticle(world, pos, 0.225, 1.55, 0.95);
					spawnParticle(world, pos, 0.025, 1.7, 0.65);

					// Right Side
					spawnParticle(world, pos, 0.225, 1.55, -0.95);
					spawnParticle(world, pos, 0.025, 1.7, -0.65);
					break;
			}
		}
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
			BlockPos blockPos = pos.offset(state.get(FACING).rotateYCounterclockwise());
			world.setBlockState(blockPos, state.with(PART, AltarPart.RIGHT), 3);
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
				BlockPos blockPos = pos.offset(state.get(FACING).rotateYCounterclockwise());
				BlockState blockState = world.getBlockState(blockPos);

				if(blockState.getBlock() == this && blockState.get(PART) == AltarPart.RIGHT)
				{
					world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 35);
					world.syncWorldEvent(player, 2001, blockPos, Block.getRawIdFromState(blockState));
				}
			}
			else if(altarPart == AltarPart.RIGHT)
			{
				BlockPos blockPos = pos.offset(state.get(FACING).rotateYClockwise());
				BlockState blockState = world.getBlockState(blockPos);

				if(blockState.getBlock() == this && blockState.get(PART) == AltarPart.LEFT)
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
		Direction direction = ctx.getPlayerFacing().getOpposite();
		BlockPos blockPos = ctx.getBlockPos();
		BlockPos blockPos2 = blockPos.offset(direction.rotateYCounterclockwise());
		return ctx.getWorld().getBlockState(blockPos2).canReplace(ctx) ? this.getDefaultState().with(FACING, direction) : null;
	}

	@Override
	public PistonBehavior getPistonBehavior(BlockState state)
	{
		return PistonBehavior.BLOCK;
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockView world)
	{
		return new AltarBlockEntity();
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved)
	{
		if(!world.isClient() && state.getBlock() != newState.getBlock())
		{
			BlockEntity blockEntity = world.getBlockEntity(pos);

			if(blockEntity instanceof Inventory)
			{
				ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
			}
		}

		super.onStateReplaced(state, world, pos, newState, moved);
	}

	public static Direction getOppositePartDirection(BlockState state)
	{
		Direction direction = state.get(FACING);
		return state.get(PART) == AltarPart.RIGHT ? direction.rotateYClockwise(): direction;
	}

	private void spawnParticle(World world, BlockPos pos, double xOffset, double yOffset, double zOffset)
	{
		double x = pos.getX() + xOffset;
		double y = pos.getY() + yOffset;
		double z = pos.getZ() + zOffset;

		world.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0D, 0.0D, 0.0D);
		world.addParticle(ParticleTypes.FLAME, x, y, z, 0.0D, 0.0D, 0.0D);
	}
}
