package tfc.better_than_scaling.mixin.quality;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockPortal;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BlockPortal.class, remap = false)
public abstract class PortalMixin {
    @Shadow
    public abstract void setBlockBoundsBasedOnState(World world, int x, int y, int z);

    @Inject(at = @At("HEAD"), method = "onEntityCollidedWithBlock", cancellable = true)
    public void preCollide(World world, int x, int y, int z, Entity entity, CallbackInfo ci) {
        setBlockBoundsBasedOnState(world, x, y, z);
        if (!entity.bb.intersectsWith(new AABB(
                ((Block) (Object) this).minX + x,
                ((Block) (Object) this).minY + y,
                ((Block) (Object) this).minZ + z,
                ((Block) (Object) this).maxX + x,
                ((Block) (Object) this).maxY + y,
                ((Block) (Object) this).maxZ + z
        )))
            ci.cancel();
    }
}
