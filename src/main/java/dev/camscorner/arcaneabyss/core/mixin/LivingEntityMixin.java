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

	public LivingEntityMixin(EntityType<?> type, World world) { super(type, world); }

	@Inject(method = "readCustomDataFromTag", at = @At("TAIL"))
	public void readNbt(CompoundTag tag, CallbackInfo info)
	{
		dataTracker.set(SOUL_CORRUPTION, tag.getInt("SoulCorruption"));
		dataTracker.set(BODY_CORRUPTION, tag.getInt("BodyCorruption"));
	}

	@Inject(method = "writeCustomDataToTag", at = @At("TAIL"))
	public void writeNbt(CompoundTag tag, CallbackInfo info)
	{
		tag.putInt("SoulCorruption", dataTracker.get(SOUL_CORRUPTION));
		tag.putInt("BodyCorruption", dataTracker.get(BODY_CORRUPTION));
	}

	@Inject(method = "initDataTracker", at = @At("TAIL"))
	public void initTracker(CallbackInfo info)
	{
		dataTracker.startTracking(SOUL_CORRUPTION, 0);
		dataTracker.startTracking(BODY_CORRUPTION, 0);
	}

	@Override
	public int getCorruption(CorruptionType type)
	{
		switch(type)
		{
			case SOUL:
				return dataTracker.get(SOUL_CORRUPTION);
			case BODY:
				return dataTracker.get(BODY_CORRUPTION);
			default:
				System.out.println("You need to define a type of corruption, numnut!");
				return 0;
		}
	}

	@Override
	public void setCorruption(CorruptionType type, int amount)
	{
		switch(type)
		{
			case SOUL:
				dataTracker.set(SOUL_CORRUPTION, amount);
				break;
			case BODY:
				dataTracker.set(BODY_CORRUPTION, amount);
				break;
			default:
				System.out.println("You need to define a type of corruption, numnut!");
		}
	}
}
