package dev.camscorner.arcaneabyss.common.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;

public class CorrodedBedrockBlock extends Block
{
	private static final IntProperty CORROSION = IntProperty.of("corrosion", 0, 2);

	public CorrodedBedrockBlock()
	{
		super(FabricBlockSettings.of(Material.STONE).strength(-1.0F, 3600000.0F).dropsNothing());
		this.setDefaultState(this.getDefaultState().with(CORROSION, 0));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		builder.add(CORROSION);
	}

	public int getCorrosion(BlockState state)
	{
		return state.get(CORROSION);
	}

	public int getMaxCorrosion()
	{
		return 2;
	}

	public BlockState withCorrosion(int age)
	{
		return this.getDefaultState().with(CORROSION, age);
	}
}
