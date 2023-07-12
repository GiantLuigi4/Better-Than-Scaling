package tfc.better_than_scaling.mixin.client.render;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityArrow;
import net.minecraft.src.RenderArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.better_than_scaling.ducks.TestCode;

@Mixin(value = RenderArrow.class, remap = false)
public class RenderArrowMixin {
    Entity arrow;

    @Inject(at = @At("HEAD"), method = "renderArrow")
    public void cacheArrow(EntityArrow entityarrow, double d, double d1, double d2, float f, float f1, CallbackInfo ci) {
        this.arrow = entityarrow;
    }

    @Redirect(method = "renderArrow", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V", ordinal = 0))
    public void redirTranslate(float x, float y, float z) {
        TestCode.translateRender(arrow, x, y, z);
    }
}
