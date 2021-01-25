package dev.camscorner.arcaneabyss.core.mixin.client;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.core.registry.AAEvents;
import dev.camscorner.arcaneabyss.core.registry.AAKeybinds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper
{
	@Unique
	private static final Identifier ARCANE_ABYSS_HUD_ELEMENTS = new Identifier(ArcaneAbyss.MOD_ID, "textures/gui/hud_elements.png");
	@Unique
	private static final double TAU = Math.PI * 2.0F;

	@Shadow
	protected abstract PlayerEntity getCameraPlayer();

	@Shadow
	private int scaledHeight, scaledWidth;

	@Shadow
	@Final
	private MinecraftClient client;

	@Shadow
	public abstract TextRenderer getFontRenderer();

	@Inject(method = "render", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/client/gui/hud/InGameHud;renderCrosshair(Lnet/minecraft/client/util/math/MatrixStack;)V", ordinal = 0))
	private void render(MatrixStack matrices, float tickDelta, CallbackInfo callbackInfo)
	{
		PlayerEntity player = getCameraPlayer();
		float spellMenuTicks = AAEvents.spellMenuTicks;
		float scale = 1F;
		double mouseX = client.mouse.getX();
		double mouseY = client.mouse.getY();
		double mouseAngle = mouseAngle(mouseX, mouseY);

		if(player != null)
		{
			List<ItemStack> filteredItems = AAEvents.filteredPlayerItems(player);

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
					if(Math.abs(mouseAngle - angleRad) < angleSize / 2 && isValidDistFromMid(0.1F))
					{
						renderTooltip(matrices, stack, (int) ((mouseX * scaledWidth / client.getWindow().getWidth()) - (scaledWidth / 2.04D)),
								(int) ((mouseY * scaledHeight / client.getWindow().getHeight()) - (scaledHeight / 2.09F)));
						RenderSystem.scalef(1.25F, 1.25F, 0F);

						if(!AAKeybinds.SPELL_MENU.isPressed() && AAKeybinds.SPELL_MENU.wasPressed())
							AAEvents.setStack(stack);
					}
					client.getItemRenderer().renderInGui(stack, (int) x, (int) y);
					RenderSystem.popMatrix();
				}

				RenderSystem.popMatrix();
				client.getTextureManager().bindTexture(GUI_ICONS_TEXTURE);
			}
		}
	}

	@Unique
	public void renderTooltip(MatrixStack matrices, ItemStack stack, int x, int y)
	{
		List<OrderedText> lines = Lists.transform(stack.getTooltip(this.client.player, this.client.options.advancedItemTooltips ? TooltipContext.Default.ADVANCED : TooltipContext.Default.NORMAL), Text::asOrderedText);

		if(!lines.isEmpty())
		{
			int i = 0;

			for(OrderedText orderedText : lines)
			{
				int j = this.getFontRenderer().getWidth(orderedText);

				if(j > i)
				{
					i = j;
				}
			}

			int k = x + 12;
			int l = y - 12;
			int n = 8;

			if(lines.size() > 1)
			{
				n += 2 + (lines.size() - 1) * 10;
			}

			if(k + i > 256)
			{
				k -= 28 + i;
			}

			if(l + n + 6 > 256)
			{
				l = 256 - n - 6;
			}

			matrices.push();
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferBuilder = tessellator.getBuffer();
			bufferBuilder.begin(7, VertexFormats.POSITION_COLOR);
			Matrix4f matrix4f = matrices.peek().getModel();
			fillGradient(matrix4f, bufferBuilder, k - 3, l - 4, k + i + 3, l - 3, 400, -267386864, -267386864);
			fillGradient(matrix4f, bufferBuilder, k - 3, l + n + 3, k + i + 3, l + n + 4, 400, -267386864, -267386864);
			fillGradient(matrix4f, bufferBuilder, k - 3, l - 3, k + i + 3, l + n + 3, 400, -267386864, -267386864);
			fillGradient(matrix4f, bufferBuilder, k - 4, l - 3, k - 3, l + n + 3, 400, -267386864, -267386864);
			fillGradient(matrix4f, bufferBuilder, k + i + 3, l - 3, k + i + 4, l + n + 3, 400, -267386864, -267386864);
			fillGradient(matrix4f, bufferBuilder, k - 3, l - 3 + 1, k - 3 + 1, l + n + 3 - 1, 400, 1347420415, 1344798847);
			fillGradient(matrix4f, bufferBuilder, k + i + 2, l - 3 + 1, k + i + 3, l + n + 3 - 1, 400, 1347420415, 1344798847);
			fillGradient(matrix4f, bufferBuilder, k - 3, l - 3, k + i + 3, l - 3 + 1, 400, 1347420415, 1347420415);
			fillGradient(matrix4f, bufferBuilder, k - 3, l + n + 2, k + i + 3, l + n + 3, 400, 1344798847, 1344798847);
			RenderSystem.enableDepthTest();
			RenderSystem.disableTexture();
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.shadeModel(7425);
			bufferBuilder.end();
			BufferRenderer.draw(bufferBuilder);
			RenderSystem.shadeModel(7424);
			RenderSystem.disableBlend();
			RenderSystem.enableTexture();
			VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
			matrices.translate(0.0D, 0.0D, 400.0D);

			for(int s = 0; s < lines.size(); ++s)
			{
				OrderedText orderedText2 = lines.get(s);

				if(orderedText2 != null)
				{
					this.getFontRenderer().draw(orderedText2, (float)k, (float)l, -1, true, matrix4f, immediate, false, 0, 15728880);
				}

				if(s == 0)
				{
					l += 2;
				}

				l += 10;
			}

			immediate.draw();
			matrices.pop();
		}
	}

	@Unique
	public boolean isValidDistFromMid(float value)
	{
		return Math.abs(client.mouse.getX() - (client.getWindow().getWidth() / 2F)) > value &&
				Math.abs(client.mouse.getY() - (client.getWindow().getHeight() / 2F)) > value;
	}

	@Unique
	public double mouseAngle(double mouseX, double mouseY)
	{
		return (Math.atan2(mouseX - (client.getWindow().getWidth() / 2F),
				(client.getWindow().getHeight() / 2F) - mouseY) + TAU) % TAU - (0.5 * Math.PI);
	}
}
