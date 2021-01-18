package dev.camscorner.arcaneabyss.core.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.core.registry.ModEvents;
import dev.camscorner.arcaneabyss.core.registry.ModKeybinds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper
{
	private static final Identifier ARCANE_ABYSS_HUD_ELEMENTS = new Identifier(ArcaneAbyss.MOD_ID, "textures/gui/hud_elements.png");
	private static final double TAU = Math.PI * 2.0F;

	@Shadow
	protected abstract PlayerEntity getCameraPlayer();

	@Shadow
	private int scaledHeight, scaledWidth;

	@Shadow
	@Final
	private MinecraftClient client;

	@Shadow private int ticks;

	@Inject(method = "render", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/client/gui/hud/InGameHud;renderCrosshair(Lnet/minecraft/client/util/math/MatrixStack;)V", ordinal = 0))
	private void render(MatrixStack matrices, float tickDelta, CallbackInfo callbackInfo)
	{
		PlayerEntity player = getCameraPlayer();
		float spellMenuTicks = ModEvents.spellMenuTicks;
		float scale = 1F;
		double mouseAngle = mouseAngle();

		if(player != null)
		{
			List<ItemStack> filteredItems = ModEvents.filteredPlayerItems(player);

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

				double angleSize = TAU / filteredItems.size();
				int i = 0;

				for(ItemStack stack : filteredItems)
				{
					i++;
					double angleRad = (i * angleSize + TAU) % TAU - (0.5 * Math.PI);
					double x = Math.cos(angleRad) * 26;
					double y = Math.sin(angleRad) * 26;

					RenderSystem.pushMatrix();
					if(Math.abs(mouseAngle - angleRad) < angleSize / 2 && isValidDistFromMid(2))
					{
						RenderSystem.scalef(1.25F, 1.25F, 0F);

						if(!ModKeybinds.SPELL_MENU.isPressed() && ModKeybinds.SPELL_MENU.wasPressed())
							ModEvents.setStack(stack);
					}
					client.getItemRenderer().renderInGui(stack, (int) x, (int) y);
					RenderSystem.popMatrix();
				}

				RenderSystem.popMatrix();
				client.getTextureManager().bindTexture(GUI_ICONS_TEXTURE);
			}
		}
	}

	public boolean isValidDistFromMid(float value)
	{
		return Math.abs(client.mouse.getX() - (client.getWindow().getWidth() / 2F)) > value &&
				Math.abs(client.mouse.getY() - (client.getWindow().getHeight() / 2F)) > value;
	}

	public double mouseAngle()
	{
		return (Math.atan2(client.mouse.getX() - (client.getWindow().getWidth() / 2F),
				(client.getWindow().getHeight() / 2F) - client.mouse.getY()) + TAU) % TAU - (0.5 * Math.PI);
	}
}
