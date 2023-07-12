package tfc.better_than_scaling.mixin.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityTrackerEntry;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.better_than_scaling.net.BulkScalePacket;

@Mixin(value = EntityTrackerEntry.class, remap = false)
public class EntityTrackerEntryMixin {
    @Shadow public Entity trackedEntity;

    @Inject(at = @At(value = "RETURN"), method = "updatePlayerEntity")
    public void postSend(EntityPlayerMP entityplayermp, CallbackInfo ci) {
        entityplayermp.playerNetServerHandler.sendPacket(new BulkScalePacket(this.trackedEntity));
    }
}
