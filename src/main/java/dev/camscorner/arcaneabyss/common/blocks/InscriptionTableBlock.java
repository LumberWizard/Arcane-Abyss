package dev.camscorner.arcaneabyss.common.blocks;

import dev.camscorner.arcaneabyss.common.blocks.entities.InscriptionTableBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class InscriptionTableBlock extends BlockWithEntity
{
	public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

	public InscriptionTableBlock(Settings settings)
	{
		super(settings);
		this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
	{
		boolean client = world.isClient;

		if(!client)
		{
			NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);

			if(screenHandlerFactory != null)
				player.openHandledScreen(screenHandlerFactory);
		}

		return ActionResult.SUCCESS;
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
	{
		Direction direction = state.get(FACING).getOpposite();

		switch(direction)
		{
			case NORTH:
				spawnParticle(world, pos, 0.85, 1.55, 0.3);
				break;
			case EAST:
				spawnParticle(world, pos, 0.7, 1.55, 0.85);
				break;
			case SOUTH:
				spawnParticle(world, pos, 0.15, 1.55, 0.7);
				break;
			case WEST:
				spawnParticle(world, pos, 0.3, 1.55, 0.15);
				break;
		}
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		builder.add(FACING);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.MODEL;
	}

	@Override
	public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos)
	{
		return true;
	}

	@Override
	public boolean hasSidedTransparency(BlockState state)
	{
		return true;
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx)
	{
		return getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation)
	{
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror)
	{
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockView world)
	{
		return new InscriptionTableBlockEntity();
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

	private void spawnParticle(World world, BlockPos pos, double xOffset, double yOffset, double zOffset)
	{
		double x = pos.getX() + xOffset;
		double y = pos.getY() + yOffset;
		double z = pos.getZ() + zOffset;

		world.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0D, 0.0D, 0.0D);
		world.addParticle(ParticleTypes.FLAME, x, y, z, 0.0D, 0.0D, 0.0D);
	}
}
