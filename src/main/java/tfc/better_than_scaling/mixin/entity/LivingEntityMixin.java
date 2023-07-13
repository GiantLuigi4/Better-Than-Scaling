package tfc.better_than_scaling.mixin.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.util.phys.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfc.better_than_scaling.api.ScaleTypes;
import tfc.better_than_scaling.ducks.EntityExtensions;
import tfc.better_than_scaling.ducks.TestCode;

@Mixin(value = EntityLiving.class, remap = false)
public abstract class LivingEntityMixin {
    @Shadow
    protected int entityAge;

    @ModifyVariable(method = "rayTrace", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public double scaleReach(double src) {
        return src * ScaleTypes.REACH.calculate((Entity) (Object) this);
    }

    @ModifyVariable(method = "causeFallDamage", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public float scaleFD(float src) {
        return src / (float) ScaleTypes.FALL_DAMAGE.calculate((Entity) (Object) this);
    }

    @ModifyConstant(method = "moveEntityWithHeading", constant = @Constant(floatValue = 4.0f))
    public float pre04f(float constant) {
        return constant / (float) ScaleTypes.MOTION.calculate((Entity) (Object) this);
    }

    @Inject(at = @At("RETURN"), method = "getHeadHeight", cancellable = true)
    public void postGetEyeHeight(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(cir.getReturnValueF() * (float) ScaleTypes.EYES.calculate((Entity) (Object) this));
    }
}
