package tfc.better_than_scaling.mixin.client;

import net.minecraft.client.entity.player.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfc.better_than_scaling.api.ScaleTypes;
import net.minecraft.core.entity.Entity;

@Mixin(value = EntityPlayerSP.class, remap = false)
public class EntityPlayerSPMixin {
    @Redirect(method = "onLivingUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/player/EntityPlayerSP;bbWidth:F"))
    public float redirWidth(EntityPlayerSP instance) {
        return instance.bbWidth * (float) ScaleTypes.WIDTH.calculate((Entity) (Object) this);
    }

    @ModifyConstant(method = "onLivingUpdate", constant = @Constant(floatValue = 0.45F))
    public float pre045F(float src) {
        return src * (float) ScaleTypes.EYES.calculate((Entity) (Object) this);
    }

    @ModifyConstant(method = "onLivingUpdate", constant = @Constant(doubleValue = 0.5D))
    public double pre05D(double src) {
        return src * (float) ScaleTypes.HEIGHT.calculate((Entity) (Object) this);
    }

    @Inject(at = @At("HEAD"), method = "checkInTile", cancellable = true)
    public void preCheckInTile(double d, double d1, double d2, CallbackInfoReturnable<Boolean> cir) {
        // TODO: make this respond to player height instead of cancelling it entirely
        if (ScaleTypes.HEIGHT.calculate((Entity) (Object) this) < 0.5)
            cir.setReturnValue(false);
    }
}
