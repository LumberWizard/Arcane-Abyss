package dev.camscorner.arcaneabyss.client.renderers.blockentity;

import dev.camscorner.arcaneabyss.common.blocks.AltarBlock;
import dev.camscorner.arcaneabyss.common.blocks.entities.AltarBlockEntity;
import dev.camscorner.arcaneabyss.common.blocks.enums.AltarPart;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.world.World;

public class AltarBlockEntityRenderer extends BlockEntityRenderer<AltarBlockEntity>
{
	private ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

	public AltarBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher)
	{
		super(dispatcher);
	}

	@Override
	public void render(AltarBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay)
	{
		World world = entity.getWorld();

		if(world != null)
		{
			BlockState state = world.getBlockState(entity.getPos());

			if(state.get(AltarBlock.PART) == AltarPart.LEFT)
			{
				int lightAbove = WorldRenderer.getLightmapCoordinates(world, entity.getPos().up());

				matrices.push();

				switch(state.get(HorizontalFacingBlock.FACING))
				{
					case NORTH:
						matrices.translate(0F, 1.35F, 0.5F);
						break;
					case EAST:
						matrices.translate(0.5F, 1.35F, 0F);
						break;
					case SOUTH:
						matrices.translate(1F, 1.35F, 0.5F);
						break;
					case WEST:
						matrices.translate(0.5F, 1.35F, 1F);
						break;
				}

				matrices.scale(0.5F, 0.5F, 0.5F);
				matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(world.getTime() * 2.8F));
				matrices.translate(0D, Math.sin(world.getTime() / 10F) / 5F, 0D);
				itemRenderer.renderItem(entity.getStack(0), ModelTransformation.Mode.FIXED, lightAbove, overlay, matrices, vertexConsumers);
				matrices.pop();
			}
		}
	}
}
