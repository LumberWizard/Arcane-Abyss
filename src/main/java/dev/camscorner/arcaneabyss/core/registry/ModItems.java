package dev.camscorner.arcaneabyss.core.registry;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;

public class ModItems
{
	//----Item Map----//
	public static final LinkedHashMap<Item, Identifier> ITEMS = new LinkedHashMap<>();

	//-----Items-----//
	public static final Item ENTROPIC_CRYSTAL = create("entropic_crystal", new Item(new Item.Settings().group(ItemGroup.MISC)));
	public static final Item STAFF = create("staff", new Item(new Item.Settings().group(ItemGroup.COMBAT)));
	public static final Item INFUSED_HOOD = create("infused_hood", new ArmorItem(ModArmourMaterials.INFUSED, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT)));
	public static final Item INFUSED_ROBES = create("infused_chest", new ArmorItem(ModArmourMaterials.INFUSED, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT)));
	public static final Item INFUSED_LEGS = create("infused_legs", new ArmorItem(ModArmourMaterials.INFUSED, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT)));

	//-----Registry-----//
	public static void register()
	{
		ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
	}

	private static <T extends Item> T create(String name, T item)
	{
		ITEMS.put(item, new Identifier(ArcaneAbyss.MOD_ID, name));
		return item;
	}
}
