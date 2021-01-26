package dev.camscorner.arcaneabyss.common.blocks.entities;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.api.util.EntropicFluxProvider;
import dev.camscorner.arcaneabyss.core.registry.AABlockEntities;
import dev.camscorner.arcaneabyss.core.registry.AADamageSource;
import dev.camscorner.arcaneabyss.core.registry.AAParticleTypes;
import dev.camscorner.arcaneabyss.core.registry.AASoundEvents;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundCategory;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntropicRiftBlockEntity extends BlockEntity implements BlockEntityClientSerializable, Tickable, EntropicFluxProvider
{
	private static final Tag<Item> LOW_FLUX = TagRegistry.item(new Identifier(ArcaneAbyss.MOD_ID, "low_flux"));
	private static final Tag<Item> MODERATE_FLUX = TagRegistry.item(new Identifier(ArcaneAbyss.MOD_ID, "moderate_flux"));
	private static final Tag<Item> HIGH_FLUX = TagRegistry.item(new Identifier(ArcaneAbyss.MOD_ID, "high_flux"));
	private static final Tag<Item> VERY_HIGH_FLUX = TagRegistry.item(new Identifier(ArcaneAbyss.MOD_ID, "very_high_flux"));
	private static final int MAX_FLUX = 1000;
	private final List<BlockPos> posList = new ArrayList<>();
	private BlockPos breakPos;
	private Box box;
	private int entropicFlux = -1;
	private boolean stabilized = false;

	public EntropicRiftBlockEntity(BlockEntityType<?> type)
	{
		super(type);
	}

	public EntropicRiftBlockEntity()
	{
		this(AABlockEntities.ENTROPIC_RIFT);
	}

	@Override
	public void fromClientTag(CompoundTag tag)
	{
		entropicFlux = tag.getInt("EntropicFlux");
		setStabilized(tag.getBoolean("Stabilized"));
	}

	@Override
	public CompoundTag toClientTag(CompoundTag tag)
	{
		tag.putInt("EntropicFlux", getEntropicFlux());
		tag.putBoolean("Stabilized", isStabilized());
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
		return super.toTag(toClientTag(tag));
	}

	@Override
	public int getEntropicFlux() {
		if (entropicFlux < 0) {
			setEntropicFlux(world.random.nextInt(ArcaneAbyss.config.maxNaturalRiftFlux) + 100);
		}
		return entropicFlux;
	}

	@Override
	public void addEntropicFlux(int amount)
	{
		setEntropicFlux(getEntropicFlux() + amount);
	}

	@Override
	public void setEntropicFlux(int amount) {
		posList.clear();
		entropicFlux = MathHelper.clamp(amount, 0, MAX_FLUX);
	}

	@Override
	public void tick()
	{
		if(world == null)
			return;

		if(box == null)
			box = new Box(pos);

		if(!stabilized)
		{
			manageEntityAttraction();

			if(!world.isClient())
			{
				if(posList.isEmpty())
					createList();

				manageBlockDestruction();
				manageSound();
			}
		}

		if(!world.isClient())
		{
			manageEntityDamage();

			if(world.getTime() % 20 == 0)
				sync();
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
						addEntropicFlux(10 * count);
					else if(MODERATE_FLUX.contains(item))
						addEntropicFlux(50 * count);
					else if(HIGH_FLUX.contains(item))
						addEntropicFlux(150 * count);
					else if(VERY_HIGH_FLUX.contains(item))
						addEntropicFlux(1000 * count);

					entity.remove();
				}
			}
			else if(entity instanceof LivingEntity)
				entity.damage(AADamageSource.ENTROPIC_RIFT, 4);
			else
				entity.remove();
		});
	}

	public void manageBlockDestruction()
	{
		int time = 80;
		int i = (int) (world.getTime() % time);
		int j = posList.size() / time;

		if(i == 0 && breakPos != null)
		{
			if(world.getBlockState(breakPos).getBlock() instanceof FluidBlock)
				world.setBlockState(breakPos, Blocks.AIR.getDefaultState());
			else
				world.breakBlock(breakPos, true);

			breakPos = null;
		}

		if(breakPos != null)
			return;

		if(i < time - 1)
		{
			for(int k = i * j; k < (i + 1) * j; k++)
			{
				if(!world.isAir(posList.get(k)) && ((world.getBlockState(posList.get(k)).getHardness(world, posList.get(k)) < 50 &&
						world.getBlockState(posList.get(k)).getHardness(world, posList.get(k)) >= 0) || world.getFluidState(posList.get(k)).isStill()))
				{
					breakPos = posList.get(k);
					break;
				}
			}
		}

		if(i == time - 1)
		{
			for(int k = i * j; k < posList.size(); k++)
			{
				if(!world.isAir(posList.get(k)) && ((world.getBlockState(posList.get(k)).getHardness(world, posList.get(k)) < 50 &&
						world.getBlockState(posList.get(k)).getHardness(world, posList.get(k)) >= 0) || world.getFluidState(posList.get(k)).isStill()))
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
			if(entity instanceof PlayerEntity && !((PlayerEntity) entity).isCreative())
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

	public void manageSound()
	{
		if(world.getTime() % 30 == 0)
			world.playSound(null, pos, AASoundEvents.RIFT_WARBLE, SoundCategory.BLOCKS, ((getEntropicFlux() * 0.015F) / 10) + 2F, 1F);
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

	public void createList()
	{
		double radius = getEntropicFlux() * 0.016;
		Iterable<BlockPos> iter = BlockPos.iterate(this.pos.add(-radius, -radius, -radius), this.pos.add(MathHelper.ceil(radius), MathHelper.ceil(radius), MathHelper.ceil(radius)));

		for(BlockPos blockPos : iter)
		{
			if(blockPos.isWithinDistance(this.pos, radius) && blockPos != pos)
			{
				posList.add(blockPos.toImmutable());
			}
		}
		Collections.shuffle(posList); //randomize with stable shuffle to make it seem more natural and chaotic
		posList.sort((o1, o2) -> (int) (this.pos.getSquaredDistance(o1.getX(), o1.getY(), o1.getZ(), false) - this.pos.getSquaredDistance(o2.getX(), o2.getY(), o2.getZ(), false)));
	}

	public Vec3d inverseDistance(Vec3d point1, Vec3d point2)
	{
		double distance = Math.max(Math.abs(point1.distanceTo(point2)), 2);
		Vec3d normVec = new Vec3d(point1.x - point2.x, point1.y - point2.y, point1.z - point2.z).normalize();
		double x = normVec.x * ((getEntropicFlux() * 0.0005) / distance);
		double y = normVec.y * ((getEntropicFlux() * 0.0005) / distance);
		double z = normVec.z * ((getEntropicFlux() * 0.0005) / distance);

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
