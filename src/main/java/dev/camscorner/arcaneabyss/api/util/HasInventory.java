package dev.camscorner.arcaneabyss.api.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface HasInventory
{
	DefaultedList<ItemStack> getInventory();
}
