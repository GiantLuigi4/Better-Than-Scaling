package tfc.better_than_scaling.mixin.entity;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityArrow;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.better_than_scaling.api.ScaleTypes;
import tfc.better_than_scaling.ducks.EntityExtensions;

@Mixin(value = EntityArrow.class, remap = false)
public class ArrowMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityArrow;setSize(FF)V", shift = At.Shift.BEFORE), method = "<init>(Lnet/minecraft/src/World;Lnet/minecraft/src/EntityLiving;ZI)V")
    public void preSetSize(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer, int arrowType, CallbackInfo ci) {
        ((EntityExtensions) this).getScaleData().merge(((EntityExtensions) entityliving).getScaleData());
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityArrow;setPosition(DDD)V"), method = "<init>(Lnet/minecraft/src/World;Lnet/minecraft/src/EntityLiving;ZI)V")
    public void preMove(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer, int arrowType, CallbackInfo ci) {
        ((Entity) (Object) this).posY += 0.1;
        float scl = (float) ScaleTypes.BASE.calculate(((EntityExtensions) this).getScaleData());
        ((Entity) (Object) this).posY -= 0.1 * scl;
    }

    @ModifyConstant(method = "<init>(Lnet/minecraft/src/World;Lnet/minecraft/src/EntityLiving;ZI)V", constant = @Constant(floatValue = 0.16f))
    public float scaleOffset1(float constant) {
        float scl = (float) ScaleTypes.BASE.calculate(((EntityExtensions) this).getScaleData());
        return constant * scl;
    }

    // scale motion
    @Redirect(method = "onUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/src/EntityArrow;motionX:D", ordinal = 17))
    public double scaleMot0(EntityArrow instance) {
        double scl = ScaleTypes.BASE.calculate(((EntityExtensions) this).getScaleData());
        return instance.motionX * scl;
    }

    @Redirect(method = "onUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/src/EntityArrow;motionZ:D", ordinal = 17))
    public double scaleMot1(EntityArrow instance) {
        double scl = ScaleTypes.BASE.calculate(((EntityExtensions) this).getScaleData());
        return instance.motionZ * scl;
    }

    @Redirect(method = "onUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/src/EntityArrow;motionY:D", ordinal = 15))
    public double scaleMot2(EntityArrow instance) {
        double scl = ScaleTypes.BASE.calculate(((EntityExtensions) this).getScaleData());
        return instance.motionY * scl;
    }

    // scale collision
    @Redirect(method = "onUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/src/EntityArrow;motionX:D", ordinal = 8))
    public double scaleMot3(EntityArrow instance) {
        double scl = ScaleTypes.BASE.calculate(((EntityExtensions) this).getScaleData());
        return instance.motionX * scl;
    }

    @Redirect(method = "onUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/src/EntityArrow;motionZ:D", ordinal = 8))
    public double scaleMot4(EntityArrow instance) {
        double scl = ScaleTypes.BASE.calculate(((EntityExtensions) this).getScaleData());
        return instance.motionZ * scl;
    }

    @Redirect(method = "onUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/src/EntityArrow;motionY:D", ordinal = 6))
    public double scaleMot5(EntityArrow instance) {
        double scl = ScaleTypes.BASE.calculate(((EntityExtensions) this).getScaleData());
        return instance.motionY * scl;
    }
}
