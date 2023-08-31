package tfc.better_than_scaling.mixin.client.render;

import net.fabricmc.loader.impl.launch.knot.KnotClient;
import net.minecraft.client.render.camera.EntityCamera;
import net.minecraft.client.render.camera.EntityCameraThirdPersonFront;
import net.minecraft.client.render.camera.EntityCameraThirdPersonRear;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfc.better_than_scaling.MinecraftCache;
import tfc.better_than_scaling.api.ScaleTypes;

@Mixin(value = EntityCameraThirdPersonRear.class, remap = false)
public class ThirdPersonCameraMixin {
    @Inject(at = @At(value = "RETURN"), method = "getCameraDistance", cancellable = true)
    public void getDist(float renderPartialTicks, CallbackInfoReturnable<Double> cir) {
        float scl = (float) ScaleTypes.THIRD_PERSON.calculate(((EntityCamera) (Object) this).entity);
        cir.setReturnValue(cir.getReturnValueD() * scl);
    }
}
