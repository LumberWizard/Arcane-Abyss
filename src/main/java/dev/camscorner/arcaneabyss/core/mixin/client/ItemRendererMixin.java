package dev.camscorner.arcaneabyss.core.mixin.client;

import dev.camscorner.arcaneabyss.ArcaneAbyss;
import dev.camscorner.arcaneabyss.core.registry.AAItems;
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
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin
{
	@Shadow
	@Final
	private ItemModels models;

	@Unique
	private final ModelIdentifier staffHandModel = new ModelIdentifier(new Identifier(ArcaneAbyss.MOD_ID, "infused_staff_in_hand"), "inventory");
	@Unique
	private final ModelIdentifier staffNormalModel = new ModelIdentifier(new Identifier(ArcaneAbyss.MOD_ID, "infused_staff"), "inventory");
	@Unique
	private final ModelIdentifier fluxThrowerHandModel = new ModelIdentifier(new Identifier(ArcaneAbyss.MOD_ID, "fluxthrower_in_hand"), "inventory");
	@Unique
	private final ModelIdentifier fluxThrowerNormalModel = new ModelIdentifier(new Identifier(ArcaneAbyss.MOD_ID, "fluxthrower"), "inventory");

	@Shadow public abstract BakedModel getHeldItemModel(ItemStack stack, World world, LivingEntity entity);

	@Inject(method = "getHeldItemModel", at = @At("HEAD"), cancellable = true)
	public void getModelProxy(ItemStack stack, World world, LivingEntity entity, CallbackInfoReturnable<BakedModel> info)
	{
		ClientWorld clientWorld = world instanceof ClientWorld ? (ClientWorld)world : null;

		if(stack.getItem() == AAItems.INFUSED_STAFF)
		{
			BakedModel customModel = models.getModelManager().getModel(staffHandModel);;
			BakedModel bakedModel = customModel.getOverrides().apply(customModel, stack, clientWorld, entity);

			info.setReturnValue(bakedModel == null ? models.getModelManager().getMissingModel() : bakedModel);
		}
		else if(stack.getItem() == AAItems.FLUXTHROWER)
		{
			BakedModel customModel = models.getModelManager().getModel(fluxThrowerHandModel);;
			BakedModel bakedModel = customModel.getOverrides().apply(customModel, stack, clientWorld, entity);

			info.setReturnValue(bakedModel == null ? models.getModelManager().getMissingModel() : bakedModel);
		}
	}

	@Redirect(method = "innerRenderInGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getHeldItemModel(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/client/render/model/BakedModel;"))
	BakedModel getHeldItemModelProxy(final ItemRenderer itemRenderer, final ItemStack stack, final World world, final LivingEntity entity)
	{
		final ClientWorld clientWorld = world instanceof ClientWorld ? (ClientWorld) world : null;

		if(stack.getItem() == AAItems.INFUSED_STAFF)
		{
			final BakedModel customModel = models.getModelManager().getModel(staffNormalModel);
			final BakedModel bakedModel = customModel.getOverrides().apply(customModel, stack, clientWorld, entity);

			return bakedModel == null ? models.getModelManager().getMissingModel() : bakedModel;
		}
		else if(stack.getItem() == AAItems.FLUXTHROWER)
		{
			final BakedModel customModel = models.getModelManager().getModel(fluxThrowerNormalModel);
			final BakedModel bakedModel = customModel.getOverrides().apply(customModel, stack, clientWorld, entity);

			return bakedModel == null ? models.getModelManager().getMissingModel() : bakedModel;
		}

		return this.getHeldItemModel(stack, world, entity);
	}
}
