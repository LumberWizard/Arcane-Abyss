package dev.camscorner.arcaneabyss.core.mixin;

import dev.camscorner.arcaneabyss.core.util.interfaces.LivingEntityProperties;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityProperties
{
	private static final TrackedData<Integer> SOUL_CORRUPTION = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private static final TrackedData<Integer> BODY_CORRUPTION = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private static final TrackedData<Integer> ENTROPIC_FLUX = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public LivingEntityMixin(EntityType<?> type, World world)
	{
		super(type, world);
	}

	@Inject(method = "readCustomDataFromTag", at = @At("TAIL"))
	public void readNbt(CompoundTag tag, CallbackInfo info)
	{
		dataTracker.set(SOUL_CORRUPTION, tag.getInt("SoulCorruption"));
		dataTracker.set(BODY_CORRUPTION, tag.getInt("BodyCorruption"));
		dataTracker.set(ENTROPIC_FLUX, tag.getInt("EntropicFlux"));
	}

	@Inject(method = "writeCustomDataToTag", at = @At("TAIL"))
	public void writeNbt(CompoundTag tag, CallbackInfo info)
	{
		tag.putInt("SoulCorruption", dataTracker.get(SOUL_CORRUPTION));
		tag.putInt("BodyCorruption", dataTracker.get(BODY_CORRUPTION));
		tag.putInt("EntropicFlux", dataTracker.get(ENTROPIC_FLUX));
	}

	@Inject(method = "initDataTracker", at = @At("TAIL"))
	public void initTracker(CallbackInfo info)
	{
		dataTracker.startTracking(SOUL_CORRUPTION, 0);
		dataTracker.startTracking(BODY_CORRUPTION, 0);
		dataTracker.startTracking(ENTROPIC_FLUX, 0);
	}

	@Override
	public boolean hasCorruption(CorruptionType type)
	{
		return getCorruption(type) > 0;
	}

	@Override
	public boolean hasEntropicFlux()
	{
		return dataTracker.get(ENTROPIC_FLUX) > 0;
	}

	@Override
	public int getCorruption(CorruptionType type)
	{
		return dataTracker.get(type == CorruptionType.SOUL ? SOUL_CORRUPTION : BODY_CORRUPTION);
	}

	@Override
	public void setCorruption(CorruptionType type, int amount)
	{
		dataTracker.set(type == CorruptionType.SOUL ? SOUL_CORRUPTION : BODY_CORRUPTION, amount);
	}

	@Override
	public int getEntropicFlux()
	{
		return dataTracker.get(ENTROPIC_FLUX);
	}

	@Override
	public void setEntropicFlux(int amount)
	{
		dataTracker.set(ENTROPIC_FLUX, amount);
	}
}
