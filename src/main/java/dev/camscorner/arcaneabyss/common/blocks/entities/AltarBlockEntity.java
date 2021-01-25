package dev.camscorner.arcaneabyss.common.blocks.entities;

import dev.camscorner.arcaneabyss.api.util.EntropicFluxProvider;
import dev.camscorner.arcaneabyss.core.registry.AABlockEntities;
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
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class AltarBlockEntity extends BlockEntity implements BlockEntityClientSerializable, Tickable, Inventory, HasInventory, EntropicFluxProvider
{
	private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
	private final BlockPos[] pedestals = { BlockPos.ORIGIN, BlockPos.ORIGIN, BlockPos.ORIGIN, BlockPos.ORIGIN, BlockPos.ORIGIN, BlockPos.ORIGIN };
	private int entropic_flux = 0;
	public boolean active = false;
	public Mode mode = Mode.IDLE;

	public AltarBlockEntity(BlockEntityType<?> type)
	{
		super(type);
	}

	public AltarBlockEntity()
	{
		this(AABlockEntities.ALTAR);
	}

	@Override
	public void tick()
	{
		if(!active)
			return;

		switch(mode)
		{
			case IDLE:
				active = false;
				break;
			case ITEM_CRAFTING:
				System.out.println("Altar at " + getPos() + "is crafting an item.");
				break;
			case SPELL_CRAFTING:
				System.out.println("Altar at " + getPos() + "is crafting a spell.");
				break;
			case RITUAL:
				System.out.println("Altar at " + getPos() + "is doing a ritual.");
				break;
		}
	}

	@Override
	public void fromClientTag(CompoundTag tag)
	{
		Inventories.fromTag(tag, inventory);
		mode = Mode.valueOf(tag.getString("Mode"));
		entropic_flux = tag.getInt("EntropicFlux");

		for(int i = 0; i < pedestals.length; ++i)
			pedestals[i] = BlockPos.fromLong(tag.getLong("PedestalPos" + i));
	}

	@Override
	public CompoundTag toClientTag(CompoundTag tag)
	{
		Inventories.toTag(tag, inventory);
		tag.putString("Mode", mode.name);
		tag.putInt("EntropicFlux", entropic_flux);

		for(int i = 0; i < pedestals.length; i++)
			tag.putLong("PedestalPos" + i, pedestals[i].asLong());

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

	@Override
	public int getEntropicFlux()
	{
		return entropic_flux;
	}

	@Override
	public void addEntropicFlux(int amount)
	{
		entropic_flux = amount;
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
