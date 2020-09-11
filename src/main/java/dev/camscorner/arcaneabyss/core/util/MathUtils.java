package dev.camscorner.arcaneabyss.core.util;

public class MathUtils
{
	public static boolean isWithinOrEqualTo(int lower, int value, int upper)
	{
		return (value <= upper && value >= lower);
	}
}
