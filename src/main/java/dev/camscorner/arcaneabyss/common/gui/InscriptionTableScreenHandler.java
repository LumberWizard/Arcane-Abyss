package dev.camscorner.arcaneabyss.common.gui;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.common.gui.slot.SpellComponentSlot;
import dev.camscorner.arcaneabyss.common.gui.slot.ItemSpecificSlot;
import dev.camscorner.arcaneabyss.core.registry.AAItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.BiConsumer;

public class InscriptionTableScreenHandler extends ScreenHandler
{
	public final Inventory inventory;
	public final PlayerEntity player;
	public final ScreenHandlerContext context;

	public InscriptionTableScreenHandler(int syncId, PlayerInventory playerInventory)
	{
		this(syncId, playerInventory, new SimpleInventory(8), ScreenHandlerContext.EMPTY);
	}

	public InscriptionTableScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, ScreenHandlerContext context)
	{
		super(ArcaneAbyss.INSCRIPTION_TABLE_SCREEN_HANDLER, syncId);
		checkSize(inventory, 8);
		this.inventory = inventory;
		this.player = playerInventory.player;
		this.context = context;
		inventory.onOpen(playerInventory.player);

		int m;
		int l;

		addSlot(new ItemSpecificSlot(inventory, 0, 8, 7, AAItems.INK_POT));
		addSlot(new ItemSpecificSlot(inventory, 1, 152, 7, AAItems.RESEARCH_SCROLL, AAItems.SPELL_PAPER));

		for(l = 0; l < 6; ++l)
		{
			addSlot(new SpellComponentSlot(inventory, l + 2, 35 + l * 18, 101));
		}

		//The player inventory
		for(m = 0; m < 3; ++m)
		{
			for(l = 0; l < 9; ++l)
			{
				this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 127 + m * 18));
			}
		}

		//The player Hotbar
		for(m = 0; m < 9; ++m)
		{
			this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 185));
		}
	}

	@Override
	public boolean onButtonClick(PlayerEntity player, int id)
	{
		int count = inventory.getStack(2).getCount() + inventory.getStack(3).getCount() + inventory.getStack(4).getCount() +
				inventory.getStack(5).getCount() + inventory.getStack(6).getCount() + inventory.getStack(7).getCount();
		boolean stacksAreValid = inventory.getStack(1).getItem() == AAItems.SPELL_PAPER &&
				inventory.getStack(0).getItem() == AAItems.INK_POT &&
				inventory.getStack(0).getMaxDamage() - inventory.getStack(0).getDamage() >= count;

		// TODO make sure the spell order is valid
		if(stacksAreValid)
		{
			ItemStack stack = new ItemStack(AAItems.SPELL_CRYSTAL);

			for(int i = 0; i < 6; i++)
			{
				stack.getOrCreateTag().putString("Component_" + i, inventory.getStack(i + 2).getOrCreateTag().getString("Component"));
				inventory.removeStack(i + 2);
			}

			if(!player.world.isClient())
				inventory.getStack(0).damage(count, player.world.random, (ServerPlayerEntity) player);

			inventory.setStack(1, stack);
		}

		return stacksAreValid;
	}

	@Override
	public void onContentChanged(Inventory inventory)
	{
		super.onContentChanged(inventory);

		for(Slot slot : slots)
		{
			if(!slot.doDrawHoveringEffect() || (!slot.getStack().isEmpty() && !slot.canInsert(slot.getStack())))
			{
				context.run((BiConsumer<World, BlockPos>) (world, blockPos) -> dropInventory(player, player.world, inventory, slot.id));
			}
		}
	}

	protected void dropInventory(PlayerEntity player, World world, Inventory inventory, int offset)
	{
		int j;

		if(!player.isAlive() || player instanceof ServerPlayerEntity && ((ServerPlayerEntity)player).isDisconnected())
		{
			for(j = offset; j < inventory.size(); ++j)
			{
				player.dropItem(inventory.removeStack(j), false);
			}

		}
		else
		{
			for(j = offset; j < inventory.size(); ++j)
			{
				player.inventory.offerOrDrop(world, inventory.removeStack(j));
			}
		}
	}

	@Override
	public boolean canUse(PlayerEntity player)
	{
		return this.inventory.canPlayerUse(player);
	}

	@Override
	public ItemStack transferSlot(PlayerEntity player, int invSlot)
	{
		ItemStack newStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(invSlot);

		if(slot != null && slot.hasStack())
		{
			ItemStack originalStack = slot.getStack();
			newStack = originalStack.copy();

			if(invSlot < this.inventory.size())
			{
				if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if(!this.insertItem(originalStack, 0, this.inventory.size(), false))
			{
				return ItemStack.EMPTY;
			}

			if(originalStack.isEmpty())
			{
				slot.setStack(ItemStack.EMPTY);
			}
			else
			{
				slot.markDirty();
			}
		}

		return newStack;
	}
}
