package dev.camscorner.arcaneabyss.common.items;

import dev.camscorner.arcaneabyss.common.entities.projectiles.FluxBlastEntity;
import dev.camscorner.arcaneabyss.core.registry.AAEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class FluxthrowerItem extends Item
{
	private boolean consecutiveHit = false;

	public FluxthrowerItem(Settings settings)
	{
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
	{
		ItemStack stack = user.getStackInHand(hand);
		int cooldown = consecutiveHit ? 10 : 16;
		user.getItemCooldownManager().set(this, cooldown);
		world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.PLAYERS,
				1.0F, 1.0F / (RANDOM.nextFloat() * 0.4F + 1.2F));
		world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_INSIDE, SoundCategory.PLAYERS,
				1.0F, 1.0F / (RANDOM.nextFloat() * 0.4F + 1.2F));

		if(!world.isClient())
		{
			FluxBlastEntity fluxBlast = new FluxBlastEntity(AAEntities.FLUX_BLAST, user, stack, world);
			fluxBlast.setProperties(user, user.pitch, user.yaw, 0.0F, 2.5F, 1.0F);
			world.spawnEntity(fluxBlast);
		}

		return TypedActionResult.consume(stack);
	}

	public void setConsecutiveHit(boolean isConsecutiveHit, ItemStack stack)
	{
		consecutiveHit = isConsecutiveHit;
		stack.getOrCreateTag().putBoolean("ConsecutiveHit", consecutiveHit);
	}
}
