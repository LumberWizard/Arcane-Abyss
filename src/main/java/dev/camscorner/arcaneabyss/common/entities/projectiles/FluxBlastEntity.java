package dev.camscorner.arcaneabyss.common.entities.projectiles;

import dev.camscorner.arcaneabyss.core.registry.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class FluxBlastEntity extends PersistentProjectileEntity
{
	public static final int MAX_AGE = 20;

	public FluxBlastEntity(EntityType<? extends PersistentProjectileEntity> entityType, LivingEntity owner, World world)
	{
		super(entityType, owner, world);
		this.setNoGravity(true);
		this.setDamage(4);
	}

	public FluxBlastEntity(World world)
	{
		super(ModEntities.FLUX_BLAST, world);
		this.setNoGravity(true);
		this.setDamage(4);
	}

	@Override
	protected float getDragInWater()
	{
		return 0.99F;
	}

	@Override
	public boolean canUsePortals()
	{
		return false;
	}

	@Override
	protected ItemStack asItemStack()
	{
		return ItemStack.EMPTY;
	}

	@Override
	protected void onEntityHit(EntityHitResult hitResult)
	{
		if(hitResult.getEntity() != this.getOwner() && hitResult.getEntity() instanceof LivingEntity)
		{
			LivingEntity target = (LivingEntity) hitResult.getEntity();
			Entity owner = this.getOwner() != null ? this.getOwner() : this;
			float damageAmount = (float) (target.hasStatusEffect(StatusEffects.WEAKNESS) ? this.getDamage() * 3 : this.getDamage());

			target.damage(DamageSource.arrow(this, owner), damageAmount);
			target.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 60, 0));
		}
	}
}
