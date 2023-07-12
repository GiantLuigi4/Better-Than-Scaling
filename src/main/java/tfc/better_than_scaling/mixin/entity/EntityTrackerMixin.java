package tfc.better_than_scaling.mixin.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityTrackerEntry;
import net.minecraft.core.world.pathfinder.IdHashMap;
import net.minecraft.server.entity.EntityTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.better_than_scaling.ducks.EntityExtensions;

@Mixin(value = EntityTracker.class, remap = false)
public class EntityTrackerMixin {
    @Shadow
    private IdHashMap trackedEntityHashTable;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/pathfinder/IdHashMap;add(ILjava/lang/Object;)V", shift = At.Shift.AFTER), method = "trackEntity(Lnet/minecraft/core/entity/Entity;IIZ)V")
    public void postTrack(Entity entity, int distance, int updateRate, boolean sendMotionUpdates, CallbackInfo ci) {
        ((EntityExtensions) entity).setTracker((EntityTrackerEntry) this.trackedEntityHashTable.get(entity.id));
    }
}
