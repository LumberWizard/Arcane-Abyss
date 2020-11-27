package dev.camscorner.arcaneabyss.core.mixin;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.core.registry.ModItems;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin
{
	@Shadow
	@Final
	private ItemModels models;

	@Shadow public abstract BakedModel getHeldItemModel(ItemStack stack, World world, LivingEntity entity);

	@Redirect(method = "getHeldItemModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemModels;getModel(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/render/model/BakedModel;"))
	BakedModel getModelProxy(final ItemModels itemModels, final ItemStack stack)
	{
		if(stack.getItem() == ModItems.STAFF)
		{
			return itemModels.getModelManager().getModel(new ModelIdentifier(new Identifier(ArcaneAbyss.MOD_ID, "infused_staff_in_hand"), "inventory"));
		}

		return itemModels.getModel(stack);
	}

	@Redirect(method = "innerRenderInGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getHeldItemModel(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/client/render/model/BakedModel;"))
	BakedModel getHeldItemModelProxy(final ItemRenderer itemRenderer, final ItemStack stack, final World world, final LivingEntity entity)
	{
		if(stack.getItem() == ModItems.STAFF)
		{
			final ClientWorld clientWorld = world instanceof ClientWorld ? (ClientWorld) world : null;
			final BakedModel model = models.getModelManager().getModel(new ModelIdentifier(new Identifier(ArcaneAbyss.MOD_ID, "infused_staff"), "inventory"));
			final BakedModel model2 = model.getOverrides().apply(model, stack, clientWorld, entity);

			return model2 == null ? models.getModelManager().getMissingModel() : model2;
		}

		return this.getHeldItemModel(stack, world, entity);
	}
}
