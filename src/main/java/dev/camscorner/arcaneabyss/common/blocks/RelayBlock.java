package dev.camscorner.arcaneabyss.common.blocks;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.Attachment;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class RelayBlock extends Block implements BlockEntityProvider
{
	public static final EnumProperty<Attachment> ATTACHMENT = Properties.ATTACHMENT;

	public RelayBlock(AbstractBlock.Settings settings)
	{
		super(settings);
		setDefaultState(getDefaultState().with(ATTACHMENT, Attachment.FLOOR));
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

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		builder.add(ATTACHMENT);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockView world)
	{
		return null;
	}
}
