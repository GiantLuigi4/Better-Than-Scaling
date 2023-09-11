package tfc.better_than_scaling.mixin.entity.player;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfc.better_than_scaling.api.ScaleTypes;
import tfc.better_than_scaling.ducks.EntityExtensions;

@Mixin(value = EntityPlayer.class, remap = false)
public abstract class PlayerEntityMixin {
    @Shadow
    public abstract float getHeadHeight();

    @Inject(at = @At("RETURN"), method = "getHeadHeight", cancellable = true)
    public void postGetEyeHeight(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(cir.getReturnValueF() * (float) ScaleTypes.EYES.calculate((Entity) (Object) this));
    }

    @Inject(at = @At("HEAD"), method = "joinEntityItemWithWorld")
    public void postDrop(EntityItem entityitem, CallbackInfo ci) {
        ((EntityExtensions) entityitem).getScaleData().merge(entityitem, ((EntityExtensions) this).getScaleData());
    }

    @ModifyConstant(method = "onLivingUpdate", constant = @Constant(doubleValue = 1.0))
    public double pre10d(double constant) {
        return constant * ScaleTypes.WIDTH.calculate((Entity) (Object) this);
    }

    @ModifyConstant(method = "onLivingUpdate", constant = @Constant(doubleValue = 0.5))
    public double pre05d(double constant) {
        return constant * ScaleTypes.HEIGHT.calculate((Entity) (Object) this);
    }

//    @ModifyConstant(method = "dropPlayerItemWithRandomChoice", constant = @Constant(floatValue = 0.1f))
//    public float pre01f(float constant) {
//        return constant * (float) ScaleTypes.EYES.calculate((Entity) (Object) this);
//    }

    @ModifyConstant(method = "dropPlayerItemWithRandomChoice", constant = @Constant(doubleValue = 0.30000001192092896, ordinal = 0))
    public double modifyDropHeight(double constant) {
        return 0.30000001192092896 * ScaleTypes.EYES.calculate((Entity) (Object) this);
    }

    @Redirect(method = "dropPlayerItemWithRandomChoice", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/player/EntityPlayer;getHeadHeight()F"))
    public float modifyDropHeight(EntityPlayer instance) {
        return 0.12f;
    }

    @ModifyConstant(method = "wakeUpPlayer", constant = @Constant(floatValue = 0.1f))
    public float preHeightOSet(float constant) {
        return (float) (constant * ScaleTypes.HEIGHT.calculate((Entity) (Object) this));
    }
}
