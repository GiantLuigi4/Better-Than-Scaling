package tfc.better_than_scaling.mixin.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import tfc.better_than_scaling.api.ScaleTypes;

@Mixin(value = EntityItem.class, remap = false)
public class ItemEntityMixin {
    @ModifyConstant(method = "clumpToNearbyStack", constant = @Constant(doubleValue = 0.5))
    public double swap05d(double constant) {
        return constant * ScaleTypes.WIDTH.calculate(((Entity) (Object) this));
    }
}
