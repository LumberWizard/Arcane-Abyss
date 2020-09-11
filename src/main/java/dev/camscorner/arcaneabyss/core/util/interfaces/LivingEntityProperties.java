package dev.camscorner.arcaneabyss.core.util.interfaces;

public interface LivingEntityProperties
{
	int getCorruption(CorruptionType type);

	void setCorruption(CorruptionType type, int amount);

	enum CorruptionType
	{
		BODY,
		SOUL
	}
}
