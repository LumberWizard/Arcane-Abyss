package dev.camscorner.arcaneabyss.core.mixin;

import dev.camscorner.arcaneabyss.api.util.EntropicFluxProvider;
import dev.camscorner.arcaneabyss.api.util.PermCorruptionProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements EntropicFluxProvider, PermCorruptionProvider
{
	@Unique
	private static final int MAX_FLUX = 1000;
	@Unique
	private static final int MAX_PERM_CORRUPTION = 1000;
	@Unique
	private static final TrackedData<Integer> ENTROPIC_FLUX = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);
	@Unique
	private static final TrackedData<Integer> PERM_CORRUPTION = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) { super(entityType, world); }

	@Inject(method = "readCustomDataFromTag", at = @At("TAIL"))
	public void readNbt(CompoundTag tag, CallbackInfo info)
	{
		dataTracker.set(ENTROPIC_FLUX, tag.getInt("EntropicFlux"));
		dataTracker.set(PERM_CORRUPTION, tag.getInt("PermCorruption"));
	}

	@Inject(method = "writeCustomDataToTag", at = @At("TAIL"))
	public void writeNbt(CompoundTag tag, CallbackInfo info)
	{
		tag.putInt("EntropicFlux", dataTracker.get(ENTROPIC_FLUX));
		tag.putInt("PermCorruption", dataTracker.get(PERM_CORRUPTION));
	}

	@Inject(method = "initDataTracker", at = @At("TAIL"))
	public void initTracker(CallbackInfo info)
	{
		dataTracker.startTracking(PERM_CORRUPTION, 0);
		dataTracker.startTracking(ENTROPIC_FLUX, 0);
	}

	@Unique
	@Override
	public int getEntropicFlux()
	{
		return dataTracker.get(ENTROPIC_FLUX);
	}

	@Unique
	@Override
	public void setEntropicFlux(int amount)
	{
		dataTracker.set(ENTROPIC_FLUX, MathHelper.clamp(dataTracker.get(ENTROPIC_FLUX) + amount, 0, MAX_FLUX));
	}

	@Unique
	@Override
	public int getPermanentCorruption()
	{
		return dataTracker.get(PERM_CORRUPTION);
	}

	@Unique
	@Override
	public void setPermanentCorruption(int amount)
	{
		dataTracker.set(PERM_CORRUPTION, MathHelper.clamp(dataTracker.get(PERM_CORRUPTION) + amount, 0, MAX_PERM_CORRUPTION));
	}
}
