package tfc.better_than_scaling.mixin.entity.player;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfc.better_than_scaling.api.ScaleTypes;

@Mixin(value = EntityPlayer.class, remap = false)
public class PlayerEntityMixin {
    @Inject(at = @At("RETURN"), method = "getEyeHeight", cancellable = true)
    public void postGetEyeHeight(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(cir.getReturnValueF() * (float) ScaleTypes.EYES.calculate((Entity) (Object) this));
    }
}
