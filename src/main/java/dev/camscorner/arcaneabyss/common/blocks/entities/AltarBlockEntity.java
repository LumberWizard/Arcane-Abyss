package dev.camscorner.arcaneabyss.common.blocks.entities;

import dev.camscorner.arcaneabyss.core.registry.ModBlockEntities;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class AltarBlockEntity extends BlockEntity implements BlockEntityClientSerializable, Tickable, Inventory
{
	private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
	private final BlockPos[] pedestals = { BlockPos.ORIGIN, BlockPos.ORIGIN, BlockPos.ORIGIN, BlockPos.ORIGIN, BlockPos.ORIGIN, BlockPos.ORIGIN };
	public boolean active = false;
	public Mode mode = Mode.IDLE;

	public AltarBlockEntity(BlockEntityType<?> type)
	{
		super(type);
	}

	public AltarBlockEntity()
	{
		this(ModBlockEntities.ALTAR);
	}

	@Override
	public void tick()
	{
		if(!active)
			return;
	}

	@Override
	public void fromClientTag(CompoundTag tag)
	{
		Inventories.fromTag(tag, inventory);
		mode = Mode.valueOf(tag.getString("Mode"));

		for(int i = 0; i < pedestals.length; ++i)
			pedestals[i] = BlockPos.fromLong(tag.getLong("PedestalPos" + i));
	}

	@Override
	public CompoundTag toClientTag(CompoundTag tag)
	{
		Inventories.toTag(tag, inventory);
		tag.putString("Mode", mode.name);

		for(int i = 0; i < pedestals.length; i++)
			tag.putLong("PedestalPos" + i, pedestals[i].asLong());

		return tag;
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

	public enum Mode
	{
		IDLE("IDLE"), ITEM_CRAFTING("ITEM_CRAFTING"), SPELL_CRAFTING("SPELL_CRAFTING"), RITUAL("RITUAL");

		public final String name;

		Mode(String name)
		{
			this.name = name;
		}
	}
}
