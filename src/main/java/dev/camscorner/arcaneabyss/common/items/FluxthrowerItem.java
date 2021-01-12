package dev.camscorner.arcaneabyss.common.items;

import dev.camscorner.arcaneabyss.common.entities.projectiles.FluxBlastEntity;
import dev.camscorner.arcaneabyss.core.registry.ModEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

		if(!world.isClient())
		{
			FluxBlastEntity fluxBlast = new FluxBlastEntity(ModEntities.FLUX_BLAST, user, stack, world);
			fluxBlast.setProperties(user, user.pitch, user.yaw, 0.0F, 2.5F, 1.0F);
			world.spawnEntity(fluxBlast);
		}

		return TypedActionResult.success(stack, world.isClient());
	}

	public void setConsecutiveHit(boolean isConsecutiveHit, ItemStack stack)
	{
		consecutiveHit = isConsecutiveHit;
		stack.getOrCreateTag().putBoolean("ConsecutiveHit", consecutiveHit);
	}
}
