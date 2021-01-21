package dev.camscorner.arcaneabyss.core.registry;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.api.ArcaneAbyssApi;
import dev.camscorner.arcaneabyss.api.spells.SpellComponent;
import dev.camscorner.arcaneabyss.common.spells.effects.*;
import dev.camscorner.arcaneabyss.common.spells.modifiers.*;
import dev.camscorner.arcaneabyss.common.spells.shapes.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;

public class ModSpellComponents
{
	//-----Spell Component Map-----//
	public static final LinkedHashMap<SpellComponent, Identifier> COMPONENTS = new LinkedHashMap<>();

	//-----Spell Shapes-----//
	public static final SpellComponent BEAM_SHAPE = create("beam_shape", new BeamShape(0.5F, false));
	public static final SpellComponent PROJECTILE_SHAPE = create("projectile_shape", new ProjectileShape(0.5F, true));
	public static final SpellComponent TOUCH_SHAPE = create("touch_shape", new TouchShape(0.5F, false));
	public static final SpellComponent TRAP_SHAPE = create("trap_shape", new TrapShape(0.5F, true));
	public static final SpellComponent AOE_SHAPE = create("area_of_effect_shape", new AoeShape(0.5F, false));
	public static final SpellComponent LOB_SHAPE = create("lob_shape", new LobShape(0.5F, true));
	public static final SpellComponent SELF_SHAPE = create("self_shape", new SelfShape(0.5F, false));

	//-----Spell Elements-----//
	public static final SpellComponent DAMAGE_EFFECT = create("damage_effect", new DamageEffect(0.5F));
	public static final SpellComponent MINE_EFFECT = create("mine_effect", new MineEffect(0.5F));
	public static final SpellComponent HEAL_EFFECT = create("heal_effect", new HealEffect(0.5F));
	public static final SpellComponent SHIELD_EFFECT = create("shield_effect", new ShieldEffect(0.5F));
	public static final SpellComponent TELEPORT_EFFECT = create("teleport_effect", new TeleportEffect(0.5F));
	public static final SpellComponent SWAP_EFFECT = create("swap_effect", new SwapEffect(0.5F));
	public static final SpellComponent CONJURE_ELEMENTAL_EFFECT = create("conjure_elemental_effect", new ConjureElementalEffect(0.5F));
	public static final SpellComponent TELEKINESIS_EFFECT = create("telekinesis_effect", new TelekinesisEffect(0.5F));
	public static final SpellComponent RESISTANCE_EFFECT = create("resistance_effect", new ResistanceEffect(0.5F));
	public static final SpellComponent JAMMER_EFFECT = create("jammer_effect", new JammerEffect(0.5F));
	public static final SpellComponent TEMPORAL_EFFECT = create("temporal_effect", new TemporalEffect(0.5F));

	//-----Spell Modifiers-----//
	public static final SpellComponent POTENCY_MODIFIER = create("potency_modifier", new PotencyModifier(0.5F, 10));
	public static final SpellComponent DURATION_MODIFIER = create("duration_modifier", new DurationModifier(0.5F, 10));
	public static final SpellComponent RANGE_MODIFIER = create("range_modifier", new RangeModifier(0.5F, 5));
	public static final SpellComponent SIZE_MODIFIER = create("size_modifier", new SizeModifier(0.5F, 2));
	public static final SpellComponent SPLIT_MODIFIER = create("split_modifier", new SplitModifier(0.5F, 4));
	public static final SpellComponent WATER_ELEMENTAL_MODIFIER = create("water_elemental_modifier", new WaterElementalModifier(0.5F, 1));
	public static final SpellComponent FIRE_ELEMENTAL_MODIFIER = create("fire_elemental_modifier", new FireElementalModifier(0.5F, 1));
	public static final SpellComponent EARTH_ELEMENTAL_MODIFIER = create("earth_elemental_modifier", new EarthElementalModifier(0.5F, 1));
	public static final SpellComponent AIR_ELEMENTAL_MODIFIER = create("air_elemental_modifier", new AirElementalModifier(0.5F, 1));
	public static final SpellComponent VOID_ELEMENTAL_MODIFIER = create("void_elemental_modifier", new VoidElementalModifier(0.5F, 1));

	//-----Registry-----//
	public static void register()
	{
		COMPONENTS.keySet().forEach(item -> Registry.register(ArcaneAbyssApi.COMPONENT, COMPONENTS.get(item), item));
	}

	private static <T extends SpellComponent> T create(String name, T spellComponent)
	{
		COMPONENTS.put(spellComponent, new Identifier(ArcaneAbyss.MOD_ID, name));
		return spellComponent;
	}
}
