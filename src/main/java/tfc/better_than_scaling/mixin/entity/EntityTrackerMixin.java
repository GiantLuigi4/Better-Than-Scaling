package tfc.better_than_scaling.mixin.entity;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityTracker;
import net.minecraft.src.EntityTrackerEntry;
import net.minecraft.src.MCHash;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.better_than_scaling.ducks.EntityExtensions;

@Mixin(value = EntityTracker.class, remap = false)
public class EntityTrackerMixin {
    @Shadow
    private MCHash trackedEntityHashTable;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/src/MCHash;addKey(ILjava/lang/Object;)V", shift = At.Shift.AFTER), method = "trackEntity(Lnet/minecraft/src/Entity;IIZ)V")
    public void postTrack(Entity entity, int distance, int updateRate, boolean sendMotionUpdates, CallbackInfo ci) {
        ((EntityExtensions) entity).setTracker((EntityTrackerEntry) this.trackedEntityHashTable.lookup(entity.entityId));
    }
}
