package dev.camscorner.arcaneabyss.api.recipes.codecs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.registry.Registry;

public class TagCodec
{
	public Item item;
	public CompoundTag tag;
	public static final Codec<TagCodec> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Registry.ITEM.fieldOf("item").forGetter(TagCodec::getItem),
			CompoundTag.CODEC.fieldOf("nbt").forGetter(TagCodec::getTag)
	).apply(instance, TagCodec::new));

	public TagCodec(Item item, CompoundTag tag)
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
