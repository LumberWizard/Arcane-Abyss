package dev.camscorner.arcaneabyss.core.registry;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.function.Consumer;

public class AAShaders
{
	//-----Shader Map-----//
	public static final LinkedHashMap<ManagedShaderEffect, Identifier> SHADERS = new LinkedHashMap<>();

	//-----Shaders-----//
	public static final ManagedShaderEffect DISTORTION_SHADER = create("distortion_shader", null);

	//-----Registry-----//
	public static void register()
	{
		if(ArcaneAbyss.config.enableRiftDistortionShader)
			ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
				DISTORTION_SHADER.render(tickDelta);
			});
	}

	private static <T extends ManagedShaderEffect> T create(String name, @Nullable Consumer<ManagedShaderEffect> effect)
	{
		if(effect == null)
			return (T) ShaderEffectManager.getInstance().manage(new Identifier(ArcaneAbyss.MOD_ID, "shaders/post" + name + ".json"));
		else
			return (T) ShaderEffectManager.getInstance().manage(new Identifier(ArcaneAbyss.MOD_ID, "shaders/post" + name + ".json"), effect);
	}
}
