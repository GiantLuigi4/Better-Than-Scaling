package tfc.better_than_scaling.mixin.client.render;

import net.minecraft.src.Entity;
import net.minecraft.src.RenderManager;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.better_than_scaling.api.ScaleTypes;
import tfc.better_than_scaling.ducks.EntityExtensions;
import tfc.better_than_scaling.ducks.TestCode;

@Mixin(value = RenderManager.class, remap = false)
public class RenderManagerMixin {
    @Inject(at = @At("HEAD"), method = "renderEntityWithPosYaw")
    public void preRender(Entity entity, double d, double d1, double d2, float f, float f1, CallbackInfo ci) {
        GL11.glPushMatrix();
        TestCode.scaleRender(entity);
    }

    @Inject(at = @At("TAIL"), method = "renderEntityWithPosYaw")
    public void postRender(Entity entity, double d, double d1, double d2, float f, float f1, CallbackInfo ci) {
        GL11.glPopMatrix();
    }
}
