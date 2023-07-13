package tfc.better_than_scaling.mixin.client.render;

import net.minecraft.client.render.entity.ArrowRenderer;
import net.minecraft.client.render.entity.CannonballRenderer;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.projectile.EntityArrow;
import net.minecraft.core.entity.projectile.EntityCannonball;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.better_than_scaling.ducks.TestCode;

@Mixin(value = CannonballRenderer.class, remap = false)
public class RenderCannonballMixin {
    Entity arrow;

    @Inject(at = @At("HEAD"), method = "RenderCannonball")
    public void cacheArrow(EntityCannonball entity, double d, double d1, double d2, float f, CallbackInfo ci) {
        this.arrow = entity;
    }

    @Redirect(method = "RenderCannonball", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V", ordinal = 0))
    public void redirTranslate(float x, float y, float z) {
        TestCode.translateRender(arrow, x, y, z);
    }
}
