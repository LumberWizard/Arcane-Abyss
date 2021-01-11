package dev.camscorner.arcaneabyss.client.renderers.blockentity;

import dev.camscorner.arcaneabyss.common.blocks.entities.PedestalBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
public class PedestalBlockEntityRenderer extends BlockEntityRenderer<PedestalBlockEntity>
{
	private ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

	public PedestalBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher)
	{
		super(dispatcher);
	}

	@Override
	public void render(PedestalBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay)
	{
		World world = entity.getWorld();

		if(world != null)
		{
			int lightAbove = WorldRenderer.getLightmapCoordinates(world, entity.getPos().up());

			matrices.push();
			matrices.translate(0.5D, 1.35D, 0.5D);
			matrices.scale(0.5F, 0.5F, 0.5F);
			matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(world.getTime() * 2.8F));
			matrices.translate(0D, Math.sin(world.getTime() / 10F) / 5F, 0D);
			itemRenderer.renderItem(entity.getStack(0), ModelTransformation.Mode.FIXED, lightAbove, overlay, matrices, vertexConsumers);
			matrices.pop();
		}
	}
}
