package dev.camscorner.arcaneabyss.api.codecs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.registry.Registry;

public class Result
{
	public Item item;
	public CompoundTag tag;
	public static final Codec<Result> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Registry.ITEM.fieldOf("item").forGetter(Result::getItem),
			CompoundTag.CODEC.fieldOf("nbt").forGetter(Result::getTag)
	).apply(instance, Result::new));

	public Result(Item item, CompoundTag tag)
	{
		this.item = item;
		this.tag = tag;
	}

	public Item getItem()
	{
		return item;
	}

	public CompoundTag getTag()
	{
		return tag;
	}
}
