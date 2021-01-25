package dev.camscorner.arcaneabyss.core.registry;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class AAKeybinds
{
	//-----Keybinding Map-----//
	public static final List<KeyBinding> KEYBINDINGS = new ArrayList<>();

	//-----Keybindings-----//
	public static final KeyBinding SPELL_MENU = create("spell_menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT);

	//-----Registry-----//
	public static void register()
	{
		KEYBINDINGS.forEach(KeyBindingHelper::registerKeyBinding);
	}

	private static KeyBinding create(String name, InputUtil.Type type, int key)
	{
		KeyBinding keyBinding = new KeyBinding("key." + ArcaneAbyss.MOD_ID + "." + name, type, key,
				"category." + ArcaneAbyss.MOD_ID + ".general");
		KEYBINDINGS.add(keyBinding);
		return keyBinding;
	}
}
