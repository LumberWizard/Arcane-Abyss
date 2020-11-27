package dev.camscorner.arcaneabyss.client.models;

import dev.camscorner.arcaneabyss.core.registry.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class InfusedArmourModel<T extends LivingEntity> extends BipedEntityModel<T>
{
	private final EquipmentSlot slot;

	private final ModelPart leftGauntletCrystal;
	private final ModelPart shoulderLeft;
	private final ModelPart leftShoulderCrystal;
	private final ModelPart rightGauntletCrystal;
	private final ModelPart shoulderRight;
	private final ModelPart rightShoulderCrystal;
	private final ModelPart leftEyeCrystal;
	private final ModelPart rightEyeCrystal;
	private final ModelPart hood;
	private final ModelPart hoodLeft;
	private final ModelPart hoodRight;
	private final ModelPart banner2;
	private final ModelPart banner2Back;
	private final ModelPart leftLegCrystal;
	private final ModelPart rightLegCrystal;

	public InfusedArmourModel(EquipmentSlot slot)
	{
		super(RenderLayer::getArmorCutoutNoCull, 1.0F, 0.0F, 80, 136);
		this.slot = slot;

		// Chest & Arms
		torso.setTextureOffset(0, 122).addCuboid(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 1.0F, 0.0F, false);
		torso.setTextureOffset(55, 80).addCuboid(-4.0F, 0.0F, 2.0F, 8.0F, 12.0F, 1.0F, 0.0F, false);
		torso.setTextureOffset(28, 76).addCuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

		leftArm.setTextureOffset(16, 106).addCuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		leftArm.setTextureOffset(62, 103).addCuboid(1.5F, 3.5F, -2.5F, 2.0F, 6.0F, 5.0F, 0.0F, false);

		leftGauntletCrystal = new ModelPart(this);
		leftGauntletCrystal.setPivot(4.0F, 10.0F, -2.0F);
		leftArm.addChild(leftGauntletCrystal);
		leftGauntletCrystal.setTextureOffset(14, 80).addCuboid(-1.0F, -4.5F, 1.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		shoulderLeft = new ModelPart(this);
		shoulderLeft.setPivot(0.0F, 0.0F, 0.0F);
		leftArm.addChild(shoulderLeft);
		setRotationAngle(shoulderLeft, 0.0F, 0.0F, 0.6109F);
		shoulderLeft.setTextureOffset(48, 115).addCuboid(-2.0F, -4.0F, -2.5F, 6.0F, 4.0F, 5.0F, 0.0F, false);

		leftShoulderCrystal = new ModelPart(this);
		leftShoulderCrystal.setPivot(0.0F, 0.0F, 0.0F);
		shoulderLeft.addChild(leftShoulderCrystal);
		leftShoulderCrystal.setTextureOffset(14, 78).addCuboid(0.0F, -4.5F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);

		rightArm.setTextureOffset(0, 102).addCuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		rightArm.setTextureOffset(18, 122).addCuboid(-3.5F, 3.5F, -2.5F, 2.0F, 6.0F, 5.0F, 0.0F, false);

		rightGauntletCrystal = new ModelPart(this);
		rightGauntletCrystal.setPivot(0.0F, 0.0F, 0.0F);
		rightArm.addChild(rightGauntletCrystal);
		rightGauntletCrystal.setTextureOffset(0, 80).addCuboid(-4.0F, 5.5F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		shoulderRight = new ModelPart(this);
		shoulderRight.setPivot(0.0F, 0.0F, 0.0F);
		rightArm.addChild(shoulderRight);
		setRotationAngle(shoulderRight, 0.0F, 0.0F, -0.6109F);
		shoulderRight.setTextureOffset(38, 92).addCuboid(-4.0F, -4.0F, -2.5F, 6.0F, 4.0F, 5.0F, 0.0F, false);

		rightShoulderCrystal = new ModelPart(this);
		rightShoulderCrystal.setPivot(0.0F, 0.0F, 0.0F);
		shoulderRight.addChild(rightShoulderCrystal);
		rightShoulderCrystal.setTextureOffset(0, 78).addCuboid(-2.0F, -4.5F, -0.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);

		// Head
		head.setTextureOffset(28, 64).addCuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 2.0F, 0.0F, false);

		leftEyeCrystal = new ModelPart(this);
		leftEyeCrystal.setPivot(0.0F, 0.0F, 0.0F);
		head.addChild(leftEyeCrystal);
		leftEyeCrystal.setTextureOffset(0, 76).addCuboid(-3.0F, -4.0F, -4.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);

		rightEyeCrystal = new ModelPart(this);
		rightEyeCrystal.setPivot(0.0F, 0.0F, 0.0F);
		head.addChild(rightEyeCrystal);
		rightEyeCrystal.setTextureOffset(0, 72).addCuboid(1.0F, -4.0F, -4.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);

		hood = new ModelPart(this);
		hood.setPivot(0.0F, 0.0F, 0.0F);
		head.addChild(hood);
		hood.setTextureOffset(0, 64).addCuboid(-4.5F, -9.0F, -5.0F, 9.0F, 2.0F, 10.0F, 0.0F, false);
		hood.setTextureOffset(38, 102).addCuboid(-4.5F, -8.0F, 3.0F, 9.0F, 10.0F, 3.0F, 0.0F, false);

		hoodLeft = new ModelPart(this);
		hoodLeft.setPivot(0.0F, 0.0F, 0.0F);
		hood.addChild(hoodLeft);
		setRotationAngle(hoodLeft, 0.0F, 0.0F, -0.1745F);
		hoodLeft.setTextureOffset(14, 86).addCuboid(4.0F, -8.0F, -5.0F, 2.0F, 10.0F, 10.0F, 0.0F, false);
		hoodLeft.setTextureOffset(0, 68).addCuboid(1.0F, 0.0F, -5.0F, 3.0F, 2.0F, 2.0F, 0.0F, false);

		hoodRight = new ModelPart(this);
		hoodRight.setPivot(0.0F, 0.0F, 0.0F);
		hood.addChild(hoodRight);
		setRotationAngle(hoodRight, 0.0F, 0.0F, 0.1745F);
		hoodRight.setTextureOffset(0, 76).addCuboid(-6.0F, -8.0F, -5.0F, 2.0F, 10.0F, 10.0F, 0.0F, false);
		hoodRight.setTextureOffset(0, 64).addCuboid(-4.0F, 0.0F, -5.0F, 3.0F, 2.0F, 2.0F, 0.0F, false);

		// Banners
		banner2 = new ModelPart(this);
		banner2.setPivot(0.0F, 12.0F, -3.0F);
		torso.addChild(banner2);
		setRotationAngle(banner2, -0.2618F, 0.0F, 0.0F);
		banner2.setTextureOffset(60, 93).addCuboid(-4.0F, -0.5F, 0.0F, 8.0F, 9.0F, 1.0F, 0.0F, false);

		banner2Back = new ModelPart(this);
		banner2Back.setPivot(0.0F, 12.0F, 3.0F);
		torso.addChild(banner2Back);
		setRotationAngle(banner2Back, 0.2618F, 0.0F, 0.0F);
		banner2Back.setTextureOffset(48, 124).addCuboid(-4.0F, -0.5F, -1.0F, 8.0F, 9.0F, 1.0F, 0.0F, false);

		// Legs
		leftLeg.setTextureOffset(32, 115).addCuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		leftLegCrystal = new ModelPart(this);
		leftLegCrystal.setPivot(0.0F, 0.0F, 0.0F);
		leftLeg.addChild(leftLegCrystal);
		leftLegCrystal.setTextureOffset(5, 79).addCuboid(1.5F, 7.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		rightLeg.setTextureOffset(48, 64).addCuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		rightLegCrystal = new ModelPart(this);
		rightLegCrystal.setPivot(0.0F, 0.0F, 0.0F);
		rightLeg.addChild(rightLegCrystal);
		rightLegCrystal.setTextureOffset(6, 76).addCuboid(-2.5F, 7.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack stack, VertexConsumer buffer, int light, int overlay, float r, float g, float b, float a)
	{
		super.render(stack, buffer, light, overlay, r, g, b, a);

		head.visible = slot == EquipmentSlot.HEAD;
		helmet.visible = slot == EquipmentSlot.HEAD;
		torso.visible = slot == EquipmentSlot.CHEST;
		leftArm.visible = slot == EquipmentSlot.CHEST;
		rightArm.visible = slot == EquipmentSlot.CHEST;
		leftLeg.visible = slot == EquipmentSlot.LEGS;
		rightLeg.visible = slot == EquipmentSlot.LEGS;

		if(MinecraftClient.getInstance().player.getEquippedStack(EquipmentSlot.HEAD).getItem() != ModItems.INFUSED_HOOD ||
				MinecraftClient.getInstance().player.getEquippedStack(EquipmentSlot.CHEST).getItem() != ModItems.INFUSED_ROBES ||
				MinecraftClient.getInstance().player.getEquippedStack(EquipmentSlot.LEGS).getItem() != ModItems.INFUSED_LEGS)
		{
			return;
		}

		ItemStack hood = MinecraftClient.getInstance().player.getEquippedStack(EquipmentSlot.HEAD);
		ItemStack robes = MinecraftClient.getInstance().player.getEquippedStack(EquipmentSlot.CHEST);
		ItemStack legs = MinecraftClient.getInstance().player.getEquippedStack(EquipmentSlot.LEGS);

		leftEyeCrystal.visible = true;//hood.getTag().getBoolean("hasLeftEyeCrystal");
		rightEyeCrystal.visible = true;//hood.getTag().getBoolean("hasRightEyeCrystal");
		leftGauntletCrystal.visible = true;//robes.getTag().getBoolean("hasLeftGauntletCrystal");;
		leftShoulderCrystal.visible = true;//robes.getTag().getBoolean("hasLeftShoulderCrystal");;
		rightGauntletCrystal.visible = true;//robes.getTag().getBoolean("hasRightGauntletCrystal");;
		rightShoulderCrystal.visible = true;//robes.getTag().getBoolean("hasRightShoulderCrystal");;
		leftLegCrystal.visible = true;//legs.getTag().getBoolean("hasLeftLegCrystal");;
		rightLegCrystal.visible = true;//legs.getTag().getBoolean("hasRightLegCrystal");;
	}

	public void setRotationAngle(ModelPart part, float x, float y, float z)
	{
		part.pitch = x;
		part.yaw = y;
		part.roll = z;
	}
}