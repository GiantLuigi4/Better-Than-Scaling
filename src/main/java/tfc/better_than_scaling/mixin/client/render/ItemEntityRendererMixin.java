package tfc.better_than_scaling.mixin.client.render;

import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.better_than_scaling.api.ScaleTypes;
import tfc.better_than_scaling.ducks.TestCode;

@Mixin(value = ItemEntityRenderer.class, remap = false)
public class ItemEntityRendererMixin {
    Entity item;
    double y;

    @Inject(at = @At("HEAD"), method = "doRenderItem")
    public void cacheItem(EntityItem entity, double d, double d1, double d2, float f, float f1, CallbackInfo ci) {
        this.item = entity;
        y = d1;
    }

    @Inject(at = @At("RETURN"), method = "doRenderItem")
    public void uncacheItem(EntityItem entity, double d, double d1, double d2, float f, float f1, CallbackInfo ci) {
        this.item = null;
    }

    @Redirect(method = "doRenderItem", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V", ordinal = 0))
    public void redirTranslate(float x, float y, float z) {
        // I don't want to understand this
        double scl = ScaleTypes.HEIGHT.calculate(item);
        y -= 0.25f * (float) (0.5f / scl);
        y += 0.1f;
        TestCode.translateRender(item, x, (y - this.y) * scl + this.y, z);
    }
}
