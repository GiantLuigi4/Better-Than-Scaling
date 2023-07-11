package tfc.better_than_scaling.mixin.entity;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.Vec3D;
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
    public abstract Vec3D getPosition(float f);

    @Shadow
    public abstract Vec3D getLook(float f);

    @Shadow
    public abstract float getEyeHeight();

    @Shadow protected int entityAge;

    @Inject(at = @At("RETURN"), method = "getEyeHeight", cancellable = true)
    public void postGetEyeHeight(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(cir.getReturnValueF() * (float) ScaleTypes.EYES.calculate((Entity) (Object) this));
    }

    @ModifyVariable(method = "rayTrace", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public double scaleReach(double src) {
        return src * (float) ScaleTypes.REACH.calculate((Entity) (Object) this);
    }

    @ModifyConstant(method = "moveEntityWithHeading", constant = @Constant(floatValue = 4.0f))
    public float pre04f(float constant) {
        return constant / (float) ScaleTypes.MOTION.calculate((Entity) (Object) this);
    }
}
