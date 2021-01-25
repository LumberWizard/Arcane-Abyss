package dev.camscorner.arcaneabyss.common.blocks.entities;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.api.util.EntropicFluxProvider;
import dev.camscorner.arcaneabyss.core.registry.ModBlockEntities;
import dev.camscorner.arcaneabyss.core.registry.ModParticleTypes;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class EntropicRiftBlockEntity extends BlockEntity implements BlockEntityClientSerializable, Tickable, EntropicFluxProvider
{
	private static final Tag<Item> LOW_FLUX = TagRegistry.item(new Identifier(ArcaneAbyss.MOD_ID, "low_flux"));
	private static final Tag<Item> MODERATE_FLUX = TagRegistry.item(new Identifier(ArcaneAbyss.MOD_ID, "moderate_flux"));
	private static final Tag<Item> HIGH_FLUX = TagRegistry.item(new Identifier(ArcaneAbyss.MOD_ID, "high_flux"));
	private static final Tag<Item> VERY_HIGH_FLUX = TagRegistry.item(new Identifier(ArcaneAbyss.MOD_ID, "very_high_flux"));
	private static final int MAX_FLUX = 1000;
	private Random rand = new Random();
	private Box box;
	private int entropic_flux;
	private boolean stabilized = false;

	public EntropicRiftBlockEntity(BlockEntityType<?> type)
	{
		super(type);
		addEntropicFlux(rand.nextInt(500) + 501);
	}

	public EntropicRiftBlockEntity()
	{
		this(ModBlockEntities.ENTROPIC_RIFT);
	}

	@Override
	public void fromClientTag(CompoundTag tag)
	{
		addEntropicFlux(tag.getInt("EntropicFlux"));
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
	public void addEntropicFlux(int amount)
	{
		entropic_flux = MathHelper.clamp(entropic_flux + amount, 0, MAX_FLUX);
	}

	@Override
	public void tick()
	{
		if(world == null)
			return;

		if(box == null)
			box = new Box(pos);

		if(!world.isClient())
		{
			manageItemDestruction();

			if(!stabilized)
			{
				manageBlockDestruction();
			}
		}

		if(world.isClient())
		{
			manageEntityAttraction();
			manageParticleEffects();
		}
	}

	public void manageItemDestruction()
	{
		world.getEntitiesByType(EntityType.ITEM, box.contract(0.75), entity -> true).forEach(itemEntity ->
		{
			if(itemEntity.hasNoGravity())
			{
				Item item = itemEntity.getStack().getItem();
				int count = itemEntity.getStack().getCount();

				if(LOW_FLUX.contains(item))
				{
					addEntropicFlux(10 * count);
				}
				else if(MODERATE_FLUX.contains(item))
				{
					addEntropicFlux(50 * count);
				}
				else if(HIGH_FLUX.contains(item))
				{
					addEntropicFlux(150 * count);
				}
				else if(VERY_HIGH_FLUX.contains(item))
				{
					addEntropicFlux(1000 * count);
				}

				itemEntity.remove();
			}
		});
	}

	public void manageBlockDestruction()
	{

	}

	public void manageEntityAttraction()
	{
		for(Entity entity : world.getOtherEntities(null, box.expand(getEntropicFlux() * 0.01)))
		{
			entity.setVelocity(entity.getVelocity().add(inverseDistance(new Vec3d(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D),
					new Vec3d(entity.getPos().x, entity.getPos().y + (entity.getHeight() / 2), entity.getPos().z))));
		}
	}

	public void manageParticleEffects()
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

	public Vec3d inverseDistance(Vec3d point1, Vec3d point2)
	{
		double distance = Math.max(Math.abs(point1.distanceTo(point2)), 2);
		Vec3d normVec = new Vec3d(point1.x - point2.x, point1.y - point2.y, point1.z - point2.z).normalize();
		double x = normVec.x * ((getEntropicFlux() * 0.001) / distance);
		double y = normVec.y * ((getEntropicFlux() * 0.001) / distance);
		double z = normVec.z * ((getEntropicFlux() * 0.001) / distance);

		return new Vec3d(x, y, z);
	}
}
