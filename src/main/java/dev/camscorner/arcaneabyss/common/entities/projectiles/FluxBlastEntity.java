package dev.camscorner.arcaneabyss.common.entities.projectiles;

import dev.camscorner.arcaneabyss.client.network.packets.CreateProjectileEntityMessage;
import dev.camscorner.arcaneabyss.common.items.FluxthrowerItem;
import dev.camscorner.arcaneabyss.core.registry.ModEntities;
import dev.camscorner.arcaneabyss.core.registry.ModStatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

import java.util.Random;

public class FluxBlastEntity extends PersistentProjectileEntity
{
	public static final int MAX_AGE = 2;
	private boolean hitEntity = false;
	private Random rand = new Random();
	private ItemStack stack;

	public FluxBlastEntity(EntityType<? extends PersistentProjectileEntity> entityType, LivingEntity owner, ItemStack stack, World world)
	{
		super(entityType, owner, world);
		this.setNoGravity(true);
		this.setDamage(4);
		this.stack = stack;
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
	protected SoundEvent getHitSound()
	{
		return SoundEvents.BLOCK_LAVA_POP;
	}

	@Override
	public Packet<?> createSpawnPacket()
	{
		return CreateProjectileEntityMessage.send(this);
	}

	@Override
	public void tick()
	{
		super.tick();

		if(world.isClient())
			for(int i = 0; i < 127; ++i)
			{
				world.addParticle(ParticleTypes.WITCH, getPos().x + rand.nextGaussian() / 2, getPos().y + rand.nextGaussian() / 3, getPos().z + rand.nextGaussian() / 2, rand.nextGaussian() / 60, 0D, rand.nextGaussian() / 60);
				//world.addParticle(ParticleTypes.SMOKE, getPos().x + rand.nextGaussian() / 2, getPos().y + rand.nextGaussian() / 3, getPos().z + rand.nextGaussian() / 2, rand.nextGaussian() / 60, 0D, rand.nextGaussian() / 60);
			}

		if(age >= MAX_AGE || getOwner() == null)
			kill();
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult)
	{
		super.onBlockHit(blockHitResult);
		kill();
	}

	@Override
	protected void onEntityHit(EntityHitResult hitResult)
	{
		if(hitResult.getEntity() != this.getOwner() && hitResult.getEntity() instanceof LivingEntity)
		{
			LivingEntity target = (LivingEntity) hitResult.getEntity();
			Entity owner = this.getOwner() != null ? this.getOwner() : this;
			float damageAmount = (float) (target.hasStatusEffect(ModStatusEffects.ENTROPIC_DECAY) ? this.getDamage() * 3 : this.getDamage());

			target.damage(DamageSource.arrow(this, owner), damageAmount);
			target.addStatusEffect(new StatusEffectInstance(ModStatusEffects.ENTROPIC_DECAY, 60, 0));
		}
	}

	@Override
	protected void onCollision(HitResult hitResult)
	{
		super.onCollision(hitResult);

		if(!world.isClient())
		{
			if(hitResult.getType() == HitResult.Type.ENTITY)
				hitEntity = true;

			((FluxthrowerItem) stack.getItem()).setConsecutiveHit(hitEntity, stack);
		}
	}
}
