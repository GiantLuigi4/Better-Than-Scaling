package tfc.better_than_scaling.mixin.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import tfc.better_than_scaling.api.ScaleTypes;
import tfc.better_than_scaling.ducks.TestCode;

@Mixin(value = EntityRenderer.class, remap = false)
public class EntityRendererMixin {
    @Shadow
    private Minecraft mc;

    @Shadow private float field_22227_s;

    @Shadow private float field_22228_r;

    @Redirect(method = "orientCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/src/EntityLiving;yOffset:F"))
    public float preGetYOffset(EntityLiving instance) {
        return TestCode.scaleCamera(instance);
    }

    @ModifyConstant(method = "orientCamera", constant = @Constant(floatValue = -0.1f))
    public float pre01f(float constant) {
        float scl = (float) ScaleTypes.EYES.calculate(mc.renderViewEntity);
        return constant * scl;
    }

    @Redirect(method = "setupViewBobbing", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V"))
    public void preTranslate(float x, float y, float z) {
        float scl = (float) ScaleTypes.VIEW.calculate(mc.renderViewEntity);
        GL11.glTranslatef(x * scl, y * scl, z * scl);
    }

    @Redirect(method = "setupViewBobbing", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V", ordinal = 0))
    public void preRotate0(float angle, float x, float y, float z) {
        float scl = (float) ScaleTypes.VIEW.calculate(mc.renderViewEntity);
        GL11.glRotatef(angle * scl, x, y, z);
    }

    @Redirect(method = "setupViewBobbing", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V", ordinal = 1))
    public void preRotate1(float angle, float x, float y, float z) {
        float scl = (float) ScaleTypes.VIEW.calculate(mc.renderViewEntity);
        GL11.glRotatef(angle * scl, x, y, z);
    }

    @Redirect(method = "setupViewBobbing", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V", ordinal = 2))
    public void preRotate2(float angle, float x, float y, float z) {
        float scl = (float) ScaleTypes.VIEW.calculate(mc.renderViewEntity);
        GL11.glRotatef(angle * (float) Math.pow(scl, 0.95), x, y, z);
    }

    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/src/EntityRenderer;field_22227_s:F"), method = "orientCamera")
    public float getDist(EntityRenderer renderer) {
        float scl = (float) ScaleTypes.THIRD_PERSON.calculate(mc.renderViewEntity);
        return this.field_22227_s * scl;
    }

    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/src/EntityRenderer;field_22228_r:F"), method = "orientCamera")
    public float getDistOld(EntityRenderer renderer) {
        float scl = (float) ScaleTypes.THIRD_PERSON.calculate(mc.renderViewEntity);
        return this.field_22228_r * scl;
    }

    @ModifyConstant(method = "func_4135_b", constant = @Constant(floatValue = 0.05f))
    public float pre005f0(float constant) {
        float scl = (float) ScaleTypes.EYES.calculate(mc.renderViewEntity);
        return constant * scl;
    }

    @ModifyConstant(method = "setupCameraTransform", constant = @Constant(floatValue = 0.05f))
    public float pre005f1(float constant) {
        float scl = (float) ScaleTypes.EYES.calculate(mc.renderViewEntity);
        return constant * scl;
    }
}
