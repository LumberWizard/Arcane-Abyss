package dev.camscorner.arcaneabyss.common.network.packets;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.core.registry.AAItems;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.impl.networking.ClientSidePacketRegistryImpl;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class SetStaffItemMessage
{
	public static final Identifier ID = new Identifier(ArcaneAbyss.MOD_ID, "set_staff_item");

	public static void send(int staffStackSlot, int spellStackSlot, boolean isAdding)
	{
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeInt(staffStackSlot);
		buf.writeInt(spellStackSlot);
		buf.writeBoolean(isAdding);

		ClientSidePacketRegistryImpl.INSTANCE.sendToServer(ID, buf);
	}

	public static void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler network, PacketByteBuf buf, PacketSender sender)
	{
		int staffStackSlot = buf.readInt();
		int spellStackSlot = buf.readInt();
		boolean isAddingItem = buf.readBoolean();

		server.execute(() ->
		{
			ItemStack spellStack = player.inventory.getStack(spellStackSlot);
			ItemStack staffStack = player.inventory.getStack(staffStackSlot);

			if(isAddingItem && !staffStack.getOrCreateTag().contains("Component_0"))
			{
				spellStack.split(1);
				staffStack.getOrCreateTag().copyFrom(spellStack.getOrCreateTag());
			}
			else if(isAddingItem && staffStack.getOrCreateTag().contains("Component_0"))
			{
				ItemStack newSpellStack = new ItemStack(AAItems.SPELL_CRYSTAL, 1);
				newSpellStack.setTag(staffStack.getOrCreateTag().copy());
				spellStack.split(1);
				player.inventory.offerOrDrop(player.world, newSpellStack);
				staffStack.setTag(spellStack.getOrCreateTag().copy());
			}
			else if(!isAddingItem && staffStack.getOrCreateTag().contains("Component_0"))
			{
				ItemStack newSpellStack = new ItemStack(AAItems.SPELL_CRYSTAL, 1);
				newSpellStack.setTag(staffStack.getOrCreateTag().copy());
				player.inventory.offerOrDrop(player.world, newSpellStack);
				staffStack.setTag(new CompoundTag());
			}
		});
	}
}
