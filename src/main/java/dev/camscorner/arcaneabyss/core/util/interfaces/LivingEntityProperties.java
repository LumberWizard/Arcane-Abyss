package dev.camscorner.arcaneabyss.core.util.interfaces;

public interface LivingEntityProperties
{
	boolean hasCorruption(CorruptionType type);

	boolean hasEntropicFlux();

	int getCorruption(CorruptionType type);

	void setCorruption(CorruptionType type, int amount);

	int getEntropicFlux();

	void setEntropicFlux(int amount);

	enum CorruptionType
	{
		BODY, SOUL
	}
}
