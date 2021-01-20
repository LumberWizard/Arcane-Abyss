package dev.camscorner.arcaneabyss.core.registry;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.api.ArcaneAbyssApi;
import dev.camscorner.arcaneabyss.common.items.FluxthrowerItem;
import dev.camscorner.arcaneabyss.common.items.SpellCrystalItem;
import dev.camscorner.arcaneabyss.common.items.StaffItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;

public class ModItems
{
	//-----Item Map-----//
	public static final LinkedHashMap<Item, Identifier> ITEMS = new LinkedHashMap<>();

	//-----Items-----//
	public static final Item ENTROPIC_CRYSTAL = create("entropic_crystal", new Item(new Item.Settings().group(ArcaneAbyssApi.ITEM_GROUP)));
	public static final Item FLUXTHROWER = create("fluxthrower", new FluxthrowerItem(new Item.Settings().group(ArcaneAbyssApi.ITEM_GROUP).maxCount(1)));
	public static final Item INFUSED_STAFF = create("infused_staff", new StaffItem(new Item.Settings().group(ArcaneAbyssApi.ITEM_GROUP).maxCount(1), 3.0D, -2.8D));
	public static final Item INFUSED_HOOD = create("infused_hood", new ArmorItem(ModArmourMaterials.INFUSED, EquipmentSlot.HEAD, new Item.Settings().group(ArcaneAbyssApi.ITEM_GROUP)));
	public static final Item INFUSED_ROBES = create("infused_robes", new ArmorItem(ModArmourMaterials.INFUSED, EquipmentSlot.CHEST, new Item.Settings().group(ArcaneAbyssApi.ITEM_GROUP)));
	public static final Item INFUSED_GRIEVES = create("infused_grieves", new ArmorItem(ModArmourMaterials.INFUSED, EquipmentSlot.LEGS, new Item.Settings().group(ArcaneAbyssApi.ITEM_GROUP)));
	public static final Item SPELL_CRYSTAL = create("spell_crystal", new SpellCrystalItem(new Item.Settings().group(ArcaneAbyssApi.ITEM_GROUP).maxCount(1)));

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
