package tfc.better_than_scaling.mixin.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.projectile.EntityArrow;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.better_than_scaling.api.ScaleTypes;
import tfc.better_than_scaling.ducks.EntityExtensions;

@Mixin(value = EntityArrow.class, remap = false)
public class ArrowMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/projectile/EntityArrow;setSize(FF)V", shift = At.Shift.BEFORE), method = "<init>(Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/EntityLiving;ZI)V")
    public void preSetSize(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer, int arrowType, CallbackInfo ci) {
        ((EntityExtensions) this).getScaleData().merge(((EntityExtensions) entityliving).getScaleData());
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/projectile/EntityArrow;setPos(DDD)V"), method = "<init>(Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/EntityLiving;ZI)V")
    public void preMove(World world, EntityLiving entityliving, boolean doesArrowBelongToPlayer, int arrowType, CallbackInfo ci) {
        ((Entity) (Object) this).y += 0.1;
        float scl = (float) ScaleTypes.BASE.calculate(((EntityExtensions) this).getScaleData());
        ((Entity) (Object) this).y -= 0.1 * scl;
    }

    @ModifyConstant(method = "<init>(Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/EntityLiving;ZI)V", constant = @Constant(floatValue = 0.16f))
    public float scaleOffset1(float constant) {
        float scl = (float) ScaleTypes.BASE.calculate(((EntityExtensions) this).getScaleData());
        return constant * scl;
    }

    // scale motion
    @Redirect(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/projectile/EntityArrow;xd:D", ordinal = 17))
    public double scaleMot0(EntityArrow instance) {
        double scl = ScaleTypes.BASE.calculate(((EntityExtensions) this).getScaleData());
        return instance.xd * scl;
    }

    @Redirect(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/projectile/EntityArrow;zd:D", ordinal = 17))
    public double scaleMot1(EntityArrow instance) {
        double scl = ScaleTypes.BASE.calculate(((EntityExtensions) this).getScaleData());
        return instance.zd * scl;
    }

    @Redirect(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/projectile/EntityArrow;yd:D", ordinal = 15))
    public double scaleMot2(EntityArrow instance) {
        double scl = ScaleTypes.BASE.calculate(((EntityExtensions) this).getScaleData());
        return instance.yd * scl;
    }

    // scale collision
    @Redirect(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/projectile/EntityArrow;xd:D", ordinal = 8))
    public double scaleMot3(EntityArrow instance) {
        double scl = ScaleTypes.BASE.calculate(((EntityExtensions) this).getScaleData());
        return instance.xd * scl;
    }

    @Redirect(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/projectile/EntityArrow;zd:D", ordinal = 8))
    public double scaleMot4(EntityArrow instance) {
        double scl = ScaleTypes.BASE.calculate(((EntityExtensions) this).getScaleData());
        return instance.zd * scl;
    }

    @Redirect(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/projectile/EntityArrow;yd:D", ordinal = 6))
    public double scaleMot5(EntityArrow instance) {
        double scl = ScaleTypes.BASE.calculate(((EntityExtensions) this).getScaleData());
        return instance.yd * scl;
    }
}
