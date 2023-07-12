package tfc.better_than_scaling.mixin.quality;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLeavesBase;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfc.better_than_scaling.ducks.BlockExtensions;
import tfc.better_than_scaling.ducks.EntityExtensions;

@Mixin(value = Block.class, remap = false)
public class BlockMixin implements BlockExtensions {
    @Unique
    boolean isLeaves = false;

    @Inject(at = @At("TAIL"), method = "<init>")
    public void postInit(String key, int id, Material material, CallbackInfo ci) {
        //noinspection ConstantConditions
        isLeaves = (Object) this instanceof BlockLeavesBase;
    }

    @Override
    public boolean isLeaves() {
        return isLeaves;
    }
}
