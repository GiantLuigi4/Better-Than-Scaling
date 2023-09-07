package tfc.better_than_scaling.mixin.quality;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import tfc.better_than_scaling.ducks.BlockExtensions;
import tfc.better_than_scaling.ducks.EntityExtensions;

@Mixin(value = EntityLiving.class, remap = false)
public class LivingEntityMixin {
    // I don't like this being a redirect
    @Redirect(method = "canClimb", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/block/Block;isClimbable(Lnet/minecraft/core/world/World;III)Z"))
    public boolean preCheck(Block instance, World world, int x, int y, int z) {
        if (((BlockExtensions) instance).isLeaves()) {
            Entity entity = (Entity) (Object) this;
            double scale = ((EntityExtensions) entity).getScaleData().getScale();
            double height = entity.bb.maxY - entity.bb.minY;
            double length = entity.bb.maxX - entity.bb.minX;
            double width = entity.bb.maxZ - entity.bb.minZ;

            double volume = height * length * width;
            if (volume < 0.5 && scale < 0.5) return true;
        }

        return instance.isClimbable(world, x, y, z);
    }
}
