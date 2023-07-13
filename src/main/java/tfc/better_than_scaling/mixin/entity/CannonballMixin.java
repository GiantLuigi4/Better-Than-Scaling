package tfc.better_than_scaling.mixin.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.projectile.EntityArrow;
import net.minecraft.core.entity.projectile.EntityCannonball;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.better_than_scaling.api.ScaleTypes;
import tfc.better_than_scaling.ducks.EntityExtensions;
import tfc.better_than_scaling.ducks.TestCode;

@Mixin(value = EntityCannonball.class, remap = false)
public class CannonballMixin {
    @Shadow public EntityLiving owner;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/projectile/EntityCannonball;setSize(FF)V", shift = At.Shift.BEFORE), method = "<init>(Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/EntityLiving;)V")
    public void preSetSize(World world, EntityLiving owner, CallbackInfo ci) {
        ((EntityExtensions) this).getScaleData().merge(((EntityExtensions) owner).getScaleData());
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/projectile/EntityCannonball;setPos(DDD)V"), method = "<init>(Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/EntityLiving;)V")
    public void preMove(World world, EntityLiving owner, CallbackInfo ci) {
//        ((Entity) (Object) this).y += 0.1;
//        float scl = (float) ScaleTypes.BASE.calculate(((EntityExtensions) this).getScaleData());
//        ((Entity) (Object) this).y -= 0.1 * scl;
    }

    @ModifyConstant(method = "<init>(Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/EntityLiving;)V", constant = @Constant(floatValue = 0.16f))
    public float scaleOffset1(float constant) {
        float scl = (float) ScaleTypes.BASE.calculate(((EntityExtensions) this).getScaleData());
        // mixin is dumber than I'd like it to be
        // hookin superiority
        TestCode.scaleArrowPos((Entity) (Object) this, owner);
        return constant * scl;
    }

    // scale motion
    @Redirect(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/projectile/EntityCannonball;xd:D", ordinal = 3))
    public double scaleMot0(EntityCannonball instance) {
        double scl = ScaleTypes.BASE.calculate(((EntityExtensions) this).getScaleData());
        return instance.xd * scl;
    }

    @Redirect(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/projectile/EntityCannonball;zd:D", ordinal = 3))
    public double scaleMot1(EntityCannonball instance) {
        double scl = ScaleTypes.BASE.calculate(((EntityExtensions) this).getScaleData());
        return instance.zd * scl;
    }

    @Redirect(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/projectile/EntityCannonball;yd:D", ordinal = 1))
    public double scaleMot2(EntityCannonball instance) {
        double scl = ScaleTypes.BASE.calculate(((EntityExtensions) this).getScaleData());
        return instance.yd * scl;
    }

    // scale collision
    @Redirect(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/projectile/EntityCannonball;xd:D", ordinal = 8))
    public double scaleMot3(EntityCannonball instance) {
        double scl = ScaleTypes.BASE.calculate(((EntityExtensions) this).getScaleData());
        return instance.xd * scl;
    }

    @Redirect(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/projectile/EntityCannonball;zd:D", ordinal = 8))
    public double scaleMot4(EntityCannonball instance) {
        double scl = ScaleTypes.BASE.calculate(((EntityExtensions) this).getScaleData());
        return instance.zd * scl;
    }

    @Redirect(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/projectile/EntityCannonball;yd:D", ordinal = 6))
    public double scaleMot5(EntityCannonball instance) {
        double scl = ScaleTypes.BASE.calculate(((EntityExtensions) this).getScaleData());
        return instance.yd * scl;
    }
}
