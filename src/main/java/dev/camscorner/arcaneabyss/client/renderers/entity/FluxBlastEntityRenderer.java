package dev.camscorner.arcaneabyss.client.renderers.entity;

import dev.camscorner.arcaneabyss.common.entities.projectiles.FluxBlastEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class FluxBlastEntityRenderer extends EntityRenderer<FluxBlastEntity>
{
	public FluxBlastEntityRenderer(EntityRenderDispatcher dispatcher)
	{
		super(dispatcher);
	}

	@Override
	public void render(FluxBlastEntity entity, float f, float g, MatrixStack matrices, VertexConsumerProvider vertexConsumer, int light)
	{
		super.render(entity, f, g, matrices, vertexConsumer, light);
	}

	@Override
	public Identifier getTexture(FluxBlastEntity entity)
	{
		return new Identifier("minecraft", "textures/particle/soul_fire_flame.png");
	}
}
