package dev.camscorner.arcaneabyss.core.util.components;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.CompoundTag;

public class SynchedIntComponent implements IntComponent, AutoSyncedComponent
{
	@Override
	public int getValue()
	{
		return 0;
	}

	@Override
	public int setValue(int value)
	{
		return 0;
	}

	@Override
	public void readFromNbt(CompoundTag compoundTag)
	{

	}

	@Override
	public void writeToNbt(CompoundTag compoundTag)
	{

	}
}
