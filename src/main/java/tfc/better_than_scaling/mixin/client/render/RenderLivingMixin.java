package tfc.better_than_scaling.mixin.client.render;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.RenderLiving;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.better_than_scaling.api.ScaleTypes;
import tfc.better_than_scaling.ducks.EntityExtensions;

@Mixin(value = RenderLiving.class, remap = false)
public class RenderLivingMixin {
    @Inject(at = @At("HEAD"), method = "func_22012_b", cancellable = true)
    public void preFunc(EntityLiving entityliving, double d, double d1, double d2, CallbackInfo ci) {
        float scl = (float) ScaleTypes.HEIGHT.calculate(entityliving);
        float sclX = (float) ScaleTypes.WIDTH.calculate(entityliving);
        GL11.glTranslatef((float)d / sclX, (float)d1 / scl, (float)d2 / sclX);
        ci.cancel();
    }
}
