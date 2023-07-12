package tfc.better_than_scaling.mixin.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.projectile.EntityCannonball;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.better_than_scaling.api.ScaleTypes;
import tfc.better_than_scaling.ducks.EntityExtensions;

@Mixin(value = EntityCannonball.class, remap = false)
public class CannonballMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/projectile/EntityCannonball;setSize(FF)V", shift = At.Shift.BEFORE), method = "<init>(Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/EntityLiving;)V")
    public void preSetSize(World world, EntityLiving owner, CallbackInfo ci) {
        ((EntityExtensions) this).getScaleData().merge(((EntityExtensions) owner).getScaleData());
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/projectile/EntityCannonball;setPos(DDD)V"), method = "<init>(Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/EntityLiving;)V")
    public void preMove(World world, EntityLiving owner, CallbackInfo ci) {
        ((Entity) (Object) this).y += 0.1;
        float scl = (float) ScaleTypes.BASE.calculate(((EntityExtensions) this).getScaleData());
        ((Entity) (Object) this).y -= 0.1 * scl;
    }
}
