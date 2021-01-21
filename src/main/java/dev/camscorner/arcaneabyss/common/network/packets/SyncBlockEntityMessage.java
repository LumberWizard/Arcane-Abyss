package dev.camscorner.arcaneabyss.common.network.packets;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.core.util.HasInventory;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.PacketContext;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class SyncBlockEntityMessage
{
	public static final Identifier ID = new Identifier(ArcaneAbyss.MOD_ID, "sync_block_entity");

	public static void send(PlayerEntity player, BlockEntity blockEntity)
	{
		if(blockEntity instanceof HasInventory)
		{
			PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
			buf.writeLong(blockEntity.getPos().asLong());
			buf.writeVarInt(((HasInventory) blockEntity).getInventory().size());

			for(int i = 0; i < ((HasInventory) blockEntity).getInventory().size(); ++i)
			{
				buf.writeItemStack(((HasInventory) blockEntity).getInventory().get(i));
			}

			ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, ID, buf);
		}
	}

	public static void handle(PacketContext context, PacketByteBuf buf)
	{
		BlockPos pos = BlockPos.fromLong(buf.readLong());
		int size = buf.readVarInt();
		ItemStack[] inventory = new ItemStack[size];

		for(int i = 0; i < size; ++i)
		{
			inventory[i] = buf.readItemStack();
		}

		context.getTaskQueue().submit(new Runnable()
		{
			@Override
			public void run()
			{
				HasInventory blockEntity = (HasInventory) MinecraftClient.getInstance().world.getBlockEntity(pos);

				for(int i = 0; i < inventory.length; i++)
				{
					blockEntity.getInventory().set(i, inventory[i]);
				}
			}
		});
	}
}
