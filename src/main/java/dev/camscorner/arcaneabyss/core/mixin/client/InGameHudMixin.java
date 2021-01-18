package dev.camscorner.arcaneabyss.core.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.core.registry.ModEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper
{
	private static final Identifier ARCANE_ABYSS_HUD_ELEMENTS = new Identifier(ArcaneAbyss.MOD_ID, "textures/gui/hud_elements.png");

	@Shadow
	protected abstract PlayerEntity getCameraPlayer();

	@Shadow
	private int scaledHeight, scaledWidth;

	@Shadow
	@Final
	private MinecraftClient client;

	@Inject(method = "render", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/client/gui/hud/InGameHud;renderCrosshair(Lnet/minecraft/client/util/math/MatrixStack;)V", ordinal = 0))
	private void render(MatrixStack matrices, float tickDelta, CallbackInfo callbackInfo)
	{
		PlayerEntity player = getCameraPlayer();
		float spellMenuTicks = ModEvents.spellMenuTicks;
		float scale = 1F;

		if(player != null)
		{
			if(spellMenuTicks > 1)
			{
				client.getTextureManager().bindTexture(ARCANE_ABYSS_HUD_ELEMENTS);
				RenderSystem.pushMatrix();
				RenderSystem.scalef(spellMenuTicks / (2.5F / scale), spellMenuTicks / (2.5F / scale), 1F);
				RenderSystem.translatef((scaledWidth - 32 * (spellMenuTicks / (2.5F / scale))) / (spellMenuTicks / (1.25F / scale)),
						(scaledHeight - 32 * (spellMenuTicks / (2.5F / scale))) / (spellMenuTicks / (1.25F / scale)), 0F);
				RenderSystem.color4f(1, 1, 1, (spellMenuTicks / 5F) * (180 / 255F));
				drawTexture(matrices, 0, 0, 0, 0, 32, 32);

				RenderSystem.color4f(1, 1, 1, spellMenuTicks / 5F);
				RenderSystem.scalef(0.5F * scale, 0.5F * scale, 1F);
				RenderSystem.translatef(24, 24, 0);

				for(int i = 0; i < player.inventory.size(); ++i)
				{
					ItemStack stack = player.inventory.getStack(i);

					if(stack.getItem() instanceof EnderPearlItem)
					{
						/** TODO
						 * all that needs to be done here is get the x and y coords to make a circle
						 * and then add the hitbox shenanigans so you can select an item from the wheel
						 */
						RenderSystem.translatef(0F, 0F, 0F);
						client.getItemRenderer().renderInGui(stack, 0, 0);
					}
				}

				RenderSystem.popMatrix();

				client.getTextureManager().bindTexture(GUI_ICONS_TEXTURE);
			}
		}
	}
}
