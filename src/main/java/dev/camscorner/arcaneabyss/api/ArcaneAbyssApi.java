package dev.camscorner.arcaneabyss.api;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.api.spells.SpellComponent;
import dev.camscorner.arcaneabyss.core.registry.ModItems;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ArcaneAbyssApi
{
	//-----Item Group-----//
	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(ArcaneAbyss.MOD_ID, "general"),
			() -> new ItemStack(ModItems.ENTROPIC_CRYSTAL));

	//-----Custom Registries-----//
	public static final Registry<SpellComponent> COMPONENT = FabricRegistryBuilder.createSimple(SpellComponent.class,
			new Identifier(ArcaneAbyss.MOD_ID,  "spell_component")).buildAndRegister();
}
