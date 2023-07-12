package tfc.better_than_scaling.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.controller.PlayerController;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfc.better_than_scaling.api.ScaleTypes;

@Mixin(value = PlayerController.class, remap = false)
public class PlayerControllerMixin {
    @Shadow
    @Final
    protected Minecraft mc;

    @Inject(at = @At("RETURN"), method = "getBlockReachDistance", cancellable = true)
    public void postGetReach0(CallbackInfoReturnable<Float> cir) {
        float reach = (float) ScaleTypes.REACH.calculate(mc.thePlayer);
        cir.setReturnValue(cir.getReturnValueF() * reach);
    }

    @Inject(at = @At("RETURN"), method = "getEntityReachDistance", cancellable = true)
    public void postGetReach1(CallbackInfoReturnable<Float> cir) {
        float reach = (float) ScaleTypes.REACH.calculate(mc.thePlayer);
        cir.setReturnValue(cir.getReturnValueF() * reach);
    }
}
