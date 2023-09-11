package tfc.better_than_scaling.mixin.quality;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.PortalHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import tfc.better_than_scaling.api.ScaleTypes;

@Mixin(value = PortalHandler.class, remap = false)
public class PortalHandlerMixin {
    @Redirect(method = "attemptToTeleportToClosestPortal", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Entity;moveTo(DDDFF)V"))
    public void postMoveTo(Entity instance, double x, double y, double z, float yRot, float xRot) {
        instance.moveTo(x, y - 0.5f + 0.5f * ScaleTypes.HEIGHT.calculate(instance), z, yRot, xRot);
    }
}
