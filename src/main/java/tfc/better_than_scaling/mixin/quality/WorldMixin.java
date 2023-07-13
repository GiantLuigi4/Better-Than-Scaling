package tfc.better_than_scaling.mixin.quality;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import tfc.better_than_scaling.api.ScaleTypes;

@Mixin(value = World.class, remap = false)
public class WorldMixin {
    @Unique
    Entity cached;

    @ModifyVariable(method = "newExplosion", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public Entity scaleExplosion(Entity entity) {
        return cached = entity;
    }

    @ModifyVariable(method = "newExplosion", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public float scaleExplosion(float f) {
        return (float) (f * ScaleTypes.EXPLOSION.calculate(cached));
    }
}
