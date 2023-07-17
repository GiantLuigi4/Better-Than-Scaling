package tfc.better_than_scaling.mixin.entity.player;

import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.monster.EntitySkeleton;
import net.minecraft.core.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfc.better_than_scaling.api.ScaleTypes;
import tfc.better_than_scaling.ducks.EntityExtensions;

@Mixin(value = EntityPlayer.class, remap = false)
public class PlayerEntityMixin {
    @Inject(at = @At("RETURN"), method = "getHeadHeight", cancellable = true)
    public void postGetEyeHeight(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(cir.getReturnValueF() * (float) ScaleTypes.EYES.calculate((Entity) (Object) this));
    }

    @Inject(at = @At("HEAD"), method = "joinEntityItemWithWorld")
    public void postDrop(EntityItem entityitem, CallbackInfo ci) {
//        ((EntityExtensions) entityitem).getScaleData().merge(((EntityExtensions) entityitem).getScaleData());
    }
}
