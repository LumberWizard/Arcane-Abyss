package dev.camscorner.arcaneabyss.core.mixin;

import dev.camscorner.arcaneabyss.api.util.TempCorruptionProvider;
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
public abstract class LivingEntityMixin extends Entity implements TempCorruptionProvider
{
	private static final TrackedData<Integer> TEMP_CORRUPTION = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public LivingEntityMixin(EntityType<?> type, World world)
	{
		super(type, world);
	}

	@Inject(method = "readCustomDataFromTag", at = @At("TAIL"))
	public void readNbt(CompoundTag tag, CallbackInfo info)
	{
		dataTracker.set(TEMP_CORRUPTION, tag.getInt("TempCorruption"));
	}

	@Inject(method = "writeCustomDataToTag", at = @At("TAIL"))
	public void writeNbt(CompoundTag tag, CallbackInfo info)
	{
		tag.putInt("TempCorruption", dataTracker.get(TEMP_CORRUPTION));
	}

	@Inject(method = "initDataTracker", at = @At("TAIL"))
	public void initTracker(CallbackInfo info)
	{
		dataTracker.startTracking(TEMP_CORRUPTION, 0);
	}

	@Override
	public int getTemporaryCorruption()
	{
		return dataTracker.get(TEMP_CORRUPTION);
	}

	@Override
	public void setTemporaryCorruption(int amount)
	{
		dataTracker.set(TEMP_CORRUPTION, amount);
	}
}
