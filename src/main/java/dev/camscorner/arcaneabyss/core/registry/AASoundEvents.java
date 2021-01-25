package dev.camscorner.arcaneabyss.core.registry;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;

public class AASoundEvents
{
	//-----Sound Map-----//
	public static final LinkedHashMap<SoundEvent, Identifier> SOUNDS = new LinkedHashMap<>();

	//-----Sound Events-----//
	public static final SoundEvent RIFT_WARBLE = create("rift_warble");

	//-----Registry-----//
	public static void register()
	{
		SOUNDS.keySet().forEach(sound -> Registry.register(Registry.SOUND_EVENT, SOUNDS.get(sound), sound));
	}

	private static SoundEvent create(String name)
	{
		SoundEvent sound = new SoundEvent(new Identifier(ArcaneAbyss.MOD_ID, name));
		SOUNDS.put(sound, new Identifier(ArcaneAbyss.MOD_ID, name));
		return sound;
	}
}
