package dev.camscorner.arcaneabyss.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.common.gui.InscriptionTableScreenHandler;
import dev.camscorner.arcaneabyss.core.registry.ModItems;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class InscriptionTableScreen extends HandledScreen<InscriptionTableScreenHandler>
{
	private static final Identifier TEXTURE_IDLE = new Identifier(ArcaneAbyss.MOD_ID, "textures/gui/container/inscription_table_idle.png");
	private static final Identifier TEXTURE_SPELL = new Identifier(ArcaneAbyss.MOD_ID, "textures/gui/container/inscription_table_spell_crafting.png");
	private static final Identifier TEXTURE_RESEARCH = new Identifier(ArcaneAbyss.MOD_ID, "textures/gui/container/inscription_table_research.png");

	public InscriptionTableScreen(InscriptionTableScreenHandler handler, PlayerInventory inventory, Text title)
	{
		super(handler, inventory, title);
		titleY = 10;
		backgroundHeight = 209;
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button)
	{
		int scaledWidth = (width - backgroundWidth) / 2;
		int scaledHeight = (height - backgroundHeight) / 2;

		double x = mouseX - (scaledWidth + backgroundWidth - 24);
		double y = mouseY - (scaledHeight + 32);

		if(x >= 0.0D && y >= 0.0D && x < 16.0D && y < 16.0D && handler.onButtonClick(client.player, 0))
		{
			this.client.interactionManager.clickButton(handler.syncId, 0);
			return true;
		}

		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY)
	{
		this.textRenderer.draw(matrices, this.title, (float) this.titleX, (float) this.titleY, 0x404040);
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY)
	{
		ItemStack stack = handler.inventory.getStack(1);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		client.getTextureManager().bindTexture(stack.isEmpty() ? TEXTURE_IDLE : stack.getItem() == ModItems.SPELL_PAPER ? TEXTURE_SPELL : TEXTURE_RESEARCH);
		int x = (width - backgroundWidth) / 2;
		int y = (height - backgroundHeight) / 2;
		drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

		if(isPointWithinBounds(152, 14 + 19, 16, 16, mouseX, mouseY) && stack.getItem() == ModItems.SPELL_PAPER)
			drawTexture(matrices, x + backgroundWidth - 24, y + 32, backgroundWidth, 16, 16, 16);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		drawMouseoverTooltip(matrices, mouseX, mouseY);
	}

	@Override
	protected void init()
	{
		super.init();
		titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
	}
}
