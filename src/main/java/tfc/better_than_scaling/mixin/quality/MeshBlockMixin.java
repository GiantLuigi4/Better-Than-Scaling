package tfc.better_than_scaling.mixin.quality;

import net.minecraft.core.block.BlockMesh;
import net.minecraft.core.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfc.better_than_scaling.ducks.EntityExtensions;

@Mixin(value = BlockMesh.class, remap = false)
public class MeshBlockMixin {
    @Inject(at = @At("HEAD"), method = "collidesWithEntity", cancellable = true)
    public void checkCollides(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        double scale = ((EntityExtensions) entity).getScaleData().getScale();
        double height = entity.bb.maxY - entity.bb.minY;
        double length = entity.bb.maxX - entity.bb.minX;
        double width = entity.bb.maxZ - entity.bb.minZ;

        double volume = height * length * width;
        if (volume < 0.5 && scale < 0.5) {
            cir.setReturnValue(false);
        }
    }
}
