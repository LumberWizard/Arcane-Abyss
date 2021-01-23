package dev.camscorner.arcaneabyss.api.util;

public interface TempCorruptionProvider
{
	int getTemporaryCorruption();

	void setTemporaryCorruption(int amount);
}
