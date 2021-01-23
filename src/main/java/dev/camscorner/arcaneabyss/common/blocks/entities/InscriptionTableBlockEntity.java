package dev.camscorner.arcaneabyss.common.blocks.entities;

import dev.camscorner.arcaneabyss.common.blocks.InscriptionTableBlock;
import dev.camscorner.arcaneabyss.common.gui.InscriptionTableScreenHandler;
import dev.camscorner.arcaneabyss.core.registry.ModBlockEntities;
import dev.camscorner.arcaneabyss.api.util.HasInventory;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

public class InscriptionTableBlockEntity extends BlockEntity implements BlockEntityClientSerializable, NamedScreenHandlerFactory, Inventory, HasInventory
{
	private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(8, ItemStack.EMPTY);
	private InscriptionTableScreenHandler handler;

	public InscriptionTableBlockEntity(BlockEntityType<?> type)
	{
		super(type);
	}

	public InscriptionTableBlockEntity()
	{
		this(ModBlockEntities.INSCRIPTION_TABLE);
	}

	@Override
	public void markDirty()
	{
		super.markDirty();
		world.setBlockState(pos, world.getBlockState(pos).with(InscriptionTableBlock.HAS_INK, !inventory.get(0).isEmpty()));
		world.setBlockState(pos, world.getBlockState(pos).with(InscriptionTableBlock.HAS_PAPER, !inventory.get(1).isEmpty()));
		handler.onContentChanged(this);
	}

	@Override
	public DefaultedList<ItemStack> getInventory()
	{
		return inventory;
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
	public Text getDisplayName()
	{
		return new TranslatableText(getCachedState().getBlock().getTranslationKey());
	}

	@Nullable
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player)
	{
		handler = new InscriptionTableScreenHandler(syncId, player.inventory, this, ScreenHandlerContext.create(world, pos));
		return handler;
	}
}
