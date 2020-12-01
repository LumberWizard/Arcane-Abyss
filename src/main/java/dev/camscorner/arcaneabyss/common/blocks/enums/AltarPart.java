package dev.camscorner.arcaneabyss.common.blocks.enums;

import net.minecraft.util.StringIdentifiable;

public enum AltarPart implements StringIdentifiable
{
	LEFT("left"),
	RIGHT("right");

	private final String name;

	AltarPart(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return this.name;
	}

	@Override
	public String asString()
	{
		return this.name;
	}
}
