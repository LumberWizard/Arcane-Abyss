package dev.camscorner.arcaneabyss.common.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class EntropicDecayStatusEffect extends StatusEffect
{
	public EntropicDecayStatusEffect()
	{
		super(StatusEffectType.HARMFUL, 0x27263D);
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier)
	{
		int tickRate = 40 >> amplifier;

		if(tickRate > 0)
		{
			return duration % tickRate == 0;
		}
		else
		{
			return true;
		}
	}

	@Override
	public void applyUpdateEffect(LivingEntity entity, int amplifier)
	{
		entity.damage(DamageSource.WITHER, 2.0F);
		entity.hurtTime = 0;
	}
}
