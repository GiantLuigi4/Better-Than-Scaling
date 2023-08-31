package tfc.better_than_scaling.mixin.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.WorldRenderer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import tfc.better_than_scaling.api.ScaleTypes;
import tfc.better_than_scaling.ducks.TestCode;

@Mixin(value = WorldRenderer.class, remap = false)
public class EntityRendererMixin {
    @Shadow
    private Minecraft mc;

    @Redirect(method = "setupViewBobbing", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V"))
    public void preTranslate(float x, float y, float z) {
        float scl = (float) ScaleTypes.VIEW.calculate(mc.thePlayer);
        GL11.glTranslatef(x * scl, y * scl, z * scl);
    }

    @Redirect(method = "setupViewBobbing", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V", ordinal = 0))
    public void preRotate0(float angle, float x, float y, float z) {
        float scl = (float) ScaleTypes.VIEW.calculate(mc.thePlayer);
        GL11.glRotatef(angle * scl, x, y, z);
    }

    @Redirect(method = "setupViewBobbing", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V", ordinal = 1))
    public void preRotate1(float angle, float x, float y, float z) {
        float scl = (float) ScaleTypes.VIEW.calculate(mc.thePlayer);
        GL11.glRotatef(angle * scl, x, y, z);
    }

    @Redirect(method = "setupViewBobbing", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V", ordinal = 2))
    public void preRotate2(float angle, float x, float y, float z) {
        float scl = (float) ScaleTypes.VIEW.calculate(mc.thePlayer);
        GL11.glRotatef(angle * (float) Math.pow(scl, 0.95), x, y, z);
    }

    @ModifyConstant(method = "setupPlayerCamera", constant = @Constant(floatValue = 0.05f))
    public float pre005f0(float constant) {
        float scl = (float) ScaleTypes.EYES.calculate(mc.thePlayer);
        return constant * scl;
    }

    @ModifyConstant(method = "setupCameraTransform", constant = @Constant(floatValue = 0.05f))
    public float pre005f1(float constant) {
        float scl = (float) ScaleTypes.EYES.calculate(mc.thePlayer);
        return constant * scl;
    }
}
