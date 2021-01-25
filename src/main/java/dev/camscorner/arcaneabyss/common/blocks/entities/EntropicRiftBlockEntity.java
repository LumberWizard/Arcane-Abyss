package dev.camscorner.arcaneabyss.common.blocks.entities;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.api.util.EntropicFluxProvider;
import dev.camscorner.arcaneabyss.core.registry.AABlockEntities;
import dev.camscorner.arcaneabyss.core.registry.AADamageSource;
import dev.camscorner.arcaneabyss.core.registry.AAParticleTypes;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntropicRiftBlockEntity extends BlockEntity implements BlockEntityClientSerializable, Tickable, EntropicFluxProvider
{
	private static final Tag<Item> LOW_FLUX = TagRegistry.item(new Identifier(ArcaneAbyss.MOD_ID, "low_flux"));
	private static final Tag<Item> MODERATE_FLUX = TagRegistry.item(new Identifier(ArcaneAbyss.MOD_ID, "moderate_flux"));
	private static final Tag<Item> HIGH_FLUX = TagRegistry.item(new Identifier(ArcaneAbyss.MOD_ID, "high_flux"));
	private static final Tag<Item> VERY_HIGH_FLUX = TagRegistry.item(new Identifier(ArcaneAbyss.MOD_ID, "very_high_flux"));
	private static final int MAX_FLUX = 1000;
	private Random rand = new Random();
	private List<BlockPos> posList = new ArrayList<>();
	private BlockPos breakPos;
	private Box box;
	private int entropicFlux;
	private boolean stabilized = false;

	public EntropicRiftBlockEntity(BlockEntityType<?> type)
	{
		super(type);
		addEntropicFlux(rand.nextInt(501) + 500);
	}

	public EntropicRiftBlockEntity()
	{
		this(AABlockEntities.ENTROPIC_RIFT);
	}

	@Override
	public void fromClientTag(CompoundTag tag)
	{
		addEntropicFlux(tag.getInt("EntropicFlux"));
		stabilized = tag.getBoolean("Stabilized");
	}

	@Override
	public CompoundTag toClientTag(CompoundTag tag)
	{
		tag.putInt("EntropicFlux", getEntropicFlux());
		tag.putBoolean("Stabilized", stabilized);
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
		return entropicFlux;
	}

	@Override
	public void addEntropicFlux(int amount)
	{
		entropicFlux = MathHelper.clamp(entropicFlux + amount, 0, MAX_FLUX);
	}

	@Override
	public void tick()
	{
		if(world == null)
			return;

		if(posList.isEmpty() && !world.isClient())
		{
			double radius = getEntropicFlux() * 0.015;
			Iterable<BlockPos> iter = BlockPos.iterate(this.pos.add(-radius, -radius, -radius), this.pos.add(radius, radius, radius));

			for(BlockPos blockPos : iter)
			{
				if(blockPos.isWithinDistance(this.pos, radius) && blockPos != pos)
				{
					posList.add(blockPos);
				}
			}

			posList.sort((o1, o2) -> (int) (this.pos.getSquaredDistance(o1) - this.pos.getSquaredDistance(o2)));

			for(int i = posList.size() - 9; i < posList.size(); i++)
				System.out.println(posList.get(i));
		}

		if(box == null)
			box = new Box(pos);

		if(!stabilized)
		{
			manageEntityAttraction();
			manageBlockDestruction();
		}

		if(!world.isClient())
		{
			manageEntityDamage();
		}
		else
		{
			manageParticleEffects();
		}
	}

	public void manageEntityDamage()
	{
		world.getOtherEntities(null, box.contract(stabilized ? 0.75 : 0)).forEach(entity ->
		{
			if(entity instanceof ItemEntity)
			{
				if(!entity.hasNoGravity())
				{
					Item item = ((ItemEntity) entity).getStack().getItem();
					int count = ((ItemEntity) entity).getStack().getCount();

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

					entity.remove();
				}
			}

			if(entity instanceof LivingEntity)
			{
				entity.damage(AADamageSource.ENTROPIC_RIFT, 4);
			}
		});
	}

	public void manageBlockDestruction()
	{
		int i = (int) (world.getTime() % 80);
		int j = posList.size() / 80;

		if(i == 0 && breakPos != null)
		{
			world.breakBlock(breakPos, true);
			breakPos = null;
		}

		if(breakPos != null)
			return;

		if(i < 79)
		{
			for(int k = i * j; k < (i + 1) * j; k++)
			{
				if(!world.isAir(posList.get(k)))
				{
					breakPos = posList.get(k);
					break;
				}
			}
		}

		if(i == 79)
		{
			for(int k = i * j; k < posList.size(); k++)
			{
				if(!world.isAir(posList.get(k)))
				{
					breakPos = posList.get(k);
					break;
				}
			}
		}
	}

	public void manageEntityAttraction()
	{
		world.getOtherEntities(null, box.expand(getEntropicFlux() * 0.015)).forEach(entity ->
		{
			if(entity instanceof PlayerEntity)
			{
				entity.setVelocity(entity.getVelocity().add(inverseDistance(new Vec3d(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D),
						new Vec3d(entity.getPos().x, entity.getPos().y + (entity.getHeight() / 2), entity.getPos().z))));
				entity.velocityDirty = true;
			}
			else
			{
				if(!world.isClient())
				{
					entity.setVelocity(entity.getVelocity().add(inverseDistance(new Vec3d(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D),
							new Vec3d(entity.getPos().x, entity.getPos().y + (entity.getHeight() / 2), entity.getPos().z))));
					entity.velocityDirty = true;
				}
			}
		});
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
						world.addParticle((ParticleEffect) AAParticleTypes.ENTROPIC_FLUX, baseX + x, baseY + y, baseZ + z,
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
		double x = normVec.x * ((getEntropicFlux() * 0.0003) / distance);
		double y = normVec.y * ((getEntropicFlux() * 0.0003) / distance);
		double z = normVec.z * ((getEntropicFlux() * 0.0003) / distance);

		return new Vec3d(x, y, z);
	}

	public boolean isStabilized()
	{
		return stabilized;
	}

	public void setStabilized(boolean stabilized)
	{
		this.stabilized = stabilized;
	}
}
