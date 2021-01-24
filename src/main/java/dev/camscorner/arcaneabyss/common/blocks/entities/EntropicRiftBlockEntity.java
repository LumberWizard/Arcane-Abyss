package dev.camscorner.arcaneabyss.common.blocks.entities;

import dev.camscorner.arcaneabyss.api.util.EntropicFluxProvider;
import dev.camscorner.arcaneabyss.core.registry.ModBlockEntities;
import dev.camscorner.arcaneabyss.core.registry.ModParticleTypes;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class EntropicRiftBlockEntity extends BlockEntity implements BlockEntityClientSerializable, Tickable, EntropicFluxProvider
{
	private static final int MAX_FLUX = 1000;
	private Random rand = new Random();
	private int entropic_flux;

	public EntropicRiftBlockEntity(BlockEntityType<?> type)
	{
		super(type);
		setEntropicFlux(rand.nextInt(500) + 501);
	}

	public EntropicRiftBlockEntity()
	{
		this(ModBlockEntities.ENTROPIC_RIFT);
	}

	@Override
	public void fromClientTag(CompoundTag tag)
	{
		setEntropicFlux(tag.getInt("EntropicFlux"));
	}

	@Override
	public CompoundTag toClientTag(CompoundTag tag)
	{
		tag.putInt("EntropicFlux", getEntropicFlux());
		return tag;
	}

	@Override
	public void fromTag(BlockState state, CompoundTag tag)
	{
		super.fromTag(state, tag);
		fromClientTag(tag);
	}

	@Override
	public CompoundTag toTag(CompoundTag tag)
	{
		return super.toTag(tag);
	}

	@Override
	public int getEntropicFlux()
	{
		return entropic_flux;
	}

	@Override
	public void setEntropicFlux(int amount)
	{
		entropic_flux = MathHelper.clamp(entropic_flux + amount, 0, MAX_FLUX);
	}

	@Override
	public void tick()
	{
		if(world == null)
			return;

		if(!world.isClient())
		{
			// TODO zuccing logic
		}

		if(world.isClient())
		{
			double radius = 0.15;
			double step = 0.02;
			double baseX = pos.getX() + 0.5D, baseY = pos.getY() + 0.5D, baseZ = pos.getZ() + 0.5D;

			for(double x = -radius; x <= radius; x += step)
			{
				for(double y = -radius; y <= radius; y += step)
				{
					for(double z = -radius; z <= radius; z += step)
					{
						double xyxSqRt = MathHelper.sqrt((x * x) + (y * y) + (z * z));

						if(xyxSqRt < radius && xyxSqRt > radius / (1 + step))
						{
							world.addParticle((ParticleEffect) ModParticleTypes.ENTROPIC_FLUX, baseX + x, baseY + y, baseZ + z,
									(baseX - (baseX + x)) / 1.75, (baseY - (baseY + y)) / 1.75, (baseZ - (baseZ + z)) / 1.75);
						}
					}
				}
			}
		}
	}
}
