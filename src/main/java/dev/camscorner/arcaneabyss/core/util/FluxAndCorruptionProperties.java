package dev.camscorner.arcaneabyss.core.util;

public interface FluxAndCorruptionProperties
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
