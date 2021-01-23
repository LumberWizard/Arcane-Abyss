package dev.camscorner.arcaneabyss.common.blocks.entities;

import dev.camscorner.arcaneabyss.core.registry.ModBlockEntities;
import dev.camscorner.arcaneabyss.api.util.HasInventory;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;

public class PedestalBlockEntity extends BlockEntity implements BlockEntityClientSerializable, Inventory, HasInventory
{
	private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

	public PedestalBlockEntity(BlockEntityType<?> type)
	{
		super(type);
	}

	public PedestalBlockEntity()
	{
		this(ModBlockEntities.PEDESTAL);
	}

	@Override
	public void fromClientTag(CompoundTag tag)
	{
		Inventories.fromTag(tag, inventory);
	}

	@Override
	public CompoundTag toClientTag(CompoundTag tag)
	{
		Inventories.toTag(tag, inventory);
		return tag;
	}

	@Override
	public void fromTag(BlockState state, CompoundTag tag)
	{
		super.fromTag(state, tag);
		fromClientTag(tag);
	}

	@Override
	public CompoundTag toTag(CompoundTag tag)
	{
		return super.toTag(toClientTag(tag));
	}

	@Override
	public int size()
	{
		return inventory.size();
	}

	@Override
	public boolean isEmpty()
	{
		return inventory.isEmpty();
	}

	@Override
	public ItemStack getStack(int slot)
	{
		return inventory.get(slot);
	}

	@Override
	public ItemStack removeStack(int slot, int amount)
	{
		return Inventories.splitStack(inventory, slot, amount);
	}

	@Override
	public ItemStack removeStack(int slot)
	{
		return Inventories.removeStack(inventory, slot);
	}

	@Override
	public void setStack(int slot, ItemStack stack)
	{
		inventory.set(slot, stack);
	}

	@Override
	public boolean canPlayerUse(PlayerEntity player)
	{
		return player.squaredDistanceTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) < 16;
	}

	@Override
	public void clear()
	{
		inventory.clear();
	}

	@Override
	public DefaultedList<ItemStack> getInventory()
	{
		return inventory;
	}
}
