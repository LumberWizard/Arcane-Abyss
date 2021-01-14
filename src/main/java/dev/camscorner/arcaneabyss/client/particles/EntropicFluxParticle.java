package dev.camscorner.arcaneabyss.client.particles;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class EntropicFluxParticle extends SpriteBillboardParticle
{
	protected EntropicFluxParticle(ClientWorld clientWorld, double posX, double posY, double posZ, double velocityX, double velocityY, double velocityZ)
	{
		super(clientWorld, posX, posY, posZ, velocityX, velocityY, velocityZ);
	}

	@Override
	public ParticleTextureSheet getType()
	{
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Override
	public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta)
	{
		super.buildGeometry(vertexConsumer, camera, tickDelta);

		MinecraftClient.getInstance().getTextureManager().getTexture(SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE).setFilter(false, false);
		RenderSystem.depthMask(false);
		RenderSystem.enableBlend();
		RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE);
		RenderSystem.alphaFunc(GL11.GL_GREATER, 0.003921569F);
		RenderSystem.disableLighting();
	}

	@Override
	public void tick()
	{
		super.tick();

		float lifeRatio = (float) age / (float) maxAge;
		scale = scale - (lifeRatio * scale);
	}

	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<DefaultParticleType>
	{
		private final SpriteProvider spriteProvider;

		public Factory(SpriteProvider spriteProvider)
		{
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double posX, double posY, double posZ, double velocityX, double velocityY, double velocityZ)
		{
			EntropicFluxParticle particle = new EntropicFluxParticle(clientWorld, posX, posY, posZ, velocityX, velocityY, velocityZ);
			particle.setSprite(spriteProvider);
			return particle;
		}
	}
}
