package dev.camscorner.arcaneabyss.client.renderers;

import dev.camscorner.arcaneabyss.common.entities.projectiles.FluxBlastEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class FluxBlastEntityRenderer extends ProjectileEntityRenderer<FluxBlastEntity>
{
	public FluxBlastEntityRenderer(EntityRenderDispatcher dispatcher)
	{
		super(dispatcher);
	}

	@Override
	public Identifier getTexture(FluxBlastEntity entity)
	{
		return null;
	}
}
