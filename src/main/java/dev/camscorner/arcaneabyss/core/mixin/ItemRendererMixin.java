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
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin
{
	@Shadow
	@Final
	private ItemModels models;

	private final ModelIdentifier staffHandModel = new ModelIdentifier(new Identifier(ArcaneAbyss.MOD_ID, "infused_staff_in_hand"), "inventory");
	private final ModelIdentifier staffNormalModel = new ModelIdentifier(new Identifier(ArcaneAbyss.MOD_ID, "infused_staff"), "inventory");

	@Shadow public abstract BakedModel getHeldItemModel(ItemStack stack, World world, LivingEntity entity);

	@Inject(method = "getHeldItemModel", at = @At("HEAD"), cancellable = true)
	public void getModelProxy(ItemStack stack, World world, LivingEntity entity, CallbackInfoReturnable<BakedModel> info)
	{
		BakedModel staffModel = models.getModelManager().getModel(staffHandModel);;
		ClientWorld clientWorld = world instanceof ClientWorld ? (ClientWorld)world : null;
		BakedModel bakedModel = staffModel.getOverrides().apply(staffModel, stack, clientWorld, entity);

		if(stack.getItem() == ModItems.INFUSED_STAFF)
		{
			info.setReturnValue(bakedModel == null ? models.getModelManager().getMissingModel() : bakedModel);
		}
	}

	@Redirect(method = "innerRenderInGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getHeldItemModel(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/client/render/model/BakedModel;"))
	BakedModel getHeldItemModelProxy(final ItemRenderer itemRenderer, final ItemStack stack, final World world, final LivingEntity entity)
	{
		if(stack.getItem() == ModItems.INFUSED_STAFF)
		{
			final ClientWorld clientWorld = world instanceof ClientWorld ? (ClientWorld) world : null;
			final BakedModel model = models.getModelManager().getModel(staffNormalModel);
			final BakedModel model2 = model.getOverrides().apply(model, stack, clientWorld, entity);

			return model2 == null ? models.getModelManager().getMissingModel() : model2;
		}

		return this.getHeldItemModel(stack, world, entity);
	}
}
