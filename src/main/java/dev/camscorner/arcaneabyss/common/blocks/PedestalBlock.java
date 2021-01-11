package dev.camscorner.arcaneabyss.common.blocks;

import dev.camscorner.arcaneabyss.client.network.packets.SyncBlockEntityMessage;
import dev.camscorner.arcaneabyss.common.blocks.entities.PedestalBlockEntity;
import dev.camscorner.arcaneabyss.common.items.StaffItem;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("ConstantConditions")
public class PedestalBlock extends Block implements BlockEntityProvider
{
	private static final VoxelShape SHAPE = VoxelShapes.union(createCuboidShape(0, 0, 0, 16, 3, 16), createCuboidShape(2, 3, 2, 14, 13, 14), createCuboidShape(0, 13, 0, 16, 16, 16));

	public PedestalBlock(Settings settings)
	{
		super(settings);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
	{
		return SHAPE;
	}

	@Override
	public PistonBehavior getPistonBehavior(BlockState state)
	{
		return PistonBehavior.BLOCK;
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
	{
		boolean client = world.isClient;
		ItemStack stack = player.getStackInHand(hand);
		PedestalBlockEntity blockEntity = (PedestalBlockEntity) world.getBlockEntity(pos);

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

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockView world)
	{
		return new PedestalBlockEntity();
	}
}
