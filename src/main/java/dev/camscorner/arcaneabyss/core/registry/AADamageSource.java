package dev.camscorner.arcaneabyss.core.registry;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import net.minecraft.entity.damage.DamageSource;

public class AADamageSource
{
	public static final DamageSource ENTROPIC_RIFT = new RiftDamageSource("rift");

	private static class RiftDamageSource extends DamageSource
	{
		protected RiftDamageSource(String name)
		{
			super(ArcaneAbyss.MOD_ID + "." + name);
			setUnblockable();
			setScaledWithDifficulty();
		}
	}
}
