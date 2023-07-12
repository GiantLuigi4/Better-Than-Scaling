package tfc.better_than_scaling.mixin.entity;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.EntityTrackerEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.better_than_scaling.net.BulkScalePacket;
import tfc.better_than_scaling.net.ScalePacket;

@Mixin(value = EntityTrackerEntry.class, remap = false)
public class EntityTrackerEntryMixin {
    @Shadow public Entity trackedEntity;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/src/NetServerHandler;sendPacket(Lnet/minecraft/src/Packet;)V", ordinal = 0), method = "updatePlayerEntity")
    public void postSend(EntityPlayerMP entityplayermp, CallbackInfo ci) {
        entityplayermp.playerNetServerHandler.sendPacket(new BulkScalePacket(this.trackedEntity));
    }
}
