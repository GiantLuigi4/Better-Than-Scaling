package tfc.better_than_scaling.mixin.client.render;

import net.minecraft.client.render.camera.EntityCameraThirdPerson;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import tfc.better_than_scaling.MinecraftCache;
import tfc.better_than_scaling.api.ScaleTypes;

@Mixin(value = EntityCameraThirdPerson.class, remap = false)
public class ThirdPersonCameraMixin {
    @Shadow
    private double cameraDistanceO;

    @Shadow
    private double cameraDistance;

    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/camera/EntityCameraThirdPerson;cameraDistance:D"), method = "getCameraDistance")
    public double getDist(EntityCameraThirdPerson renderer) {
        float scl = (float) ScaleTypes.THIRD_PERSON.calculate(MinecraftCache.MINECRAFT.thePlayer);
        return this.cameraDistance * scl;
    }

    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/camera/EntityCameraThirdPerson;cameraDistanceO:D"), method = "getCameraDistance")
    public double getDistOld(EntityCameraThirdPerson renderer) {
        float scl = (float) ScaleTypes.THIRD_PERSON.calculate(MinecraftCache.MINECRAFT.thePlayer);
        return this.cameraDistanceO * scl;
    }
}
