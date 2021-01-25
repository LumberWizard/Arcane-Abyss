package dev.camscorner.arcaneabyss.core.registry;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.api.ArcaneAbyssApi;
import dev.camscorner.arcaneabyss.common.items.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;

public class AAItems
{
	//-----Item Map-----//
	public static final LinkedHashMap<Item, Identifier> ITEMS = new LinkedHashMap<>();

	//-----Items-----//
	public static final Item ENTROPIC_CRYSTAL = create("entropic_crystal", new Item(new Item.Settings().group(ArcaneAbyssApi.ITEM_GROUP)));
	public static final Item INFUSED_CLOTH = create("infused_cloth", new Item(new Item.Settings().group(ArcaneAbyssApi.ITEM_GROUP)));
	public static final Item SPELL_PAPER = create("spell_paper", new Item(new Item.Settings().group(ArcaneAbyssApi.ITEM_GROUP).maxCount(1)));
	public static final Item FLUXTHROWER = create("fluxthrower", new FluxthrowerItem(new Item.Settings().group(ArcaneAbyssApi.ITEM_GROUP).maxCount(1)));
	public static final Item INFUSED_STAFF = create("infused_staff", new StaffItem(new Item.Settings().group(ArcaneAbyssApi.ITEM_GROUP).maxCount(1), 3.0D, -2.8D));
	public static final Item INFUSED_HOOD = create("infused_hood", new ArmorItem(AAArmourMaterials.INFUSED, EquipmentSlot.HEAD, new Item.Settings().group(ArcaneAbyssApi.ITEM_GROUP)));
	public static final Item INFUSED_ROBES = create("infused_robes", new ArmorItem(AAArmourMaterials.INFUSED, EquipmentSlot.CHEST, new Item.Settings().group(ArcaneAbyssApi.ITEM_GROUP)));
	public static final Item INFUSED_GRIEVES = create("infused_grieves", new ArmorItem(AAArmourMaterials.INFUSED, EquipmentSlot.LEGS, new Item.Settings().group(ArcaneAbyssApi.ITEM_GROUP)));
	public static final Item SPELL_CRYSTAL = create("spell_crystal", new SpellCrystalItem(new Item.Settings().group(ArcaneAbyssApi.ITEM_GROUP).maxCount(1)));
	public static final Item INK_POT = create("ink_pot_and_quill", new Item(new Item.Settings().group(ArcaneAbyssApi.ITEM_GROUP).maxCount(1).maxDamage(64)));
	public static final Item RUNIC_STONE = create("runic_stone", new RunicStoneItem(new Item.Settings().group(ArcaneAbyssApi.ITEM_GROUP).maxCount(1)));
	public static final Item RESEARCH_SCROLL = create("research_scroll", new ResearchScrollItem(new Item.Settings().maxCount(16)));

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
