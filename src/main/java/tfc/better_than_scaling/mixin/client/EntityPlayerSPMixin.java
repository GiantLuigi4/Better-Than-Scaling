package tfc.better_than_scaling.mixin.client;

import net.minecraft.client.entity.player.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import tfc.better_than_scaling.api.ScaleTypes;
import net.minecraft.core.entity.Entity;

@Mixin(value = EntityPlayerSP.class, remap = false)
public class EntityPlayerSPMixin {
    @Redirect(method = "onLivingUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/player/EntityPlayerSP;bbWidth:F"))
    public float redirWidth(EntityPlayerSP instance) {
        return instance.bbWidth * (float) ScaleTypes.WIDTH.calculate((Entity) (Object) this);
    }

    @ModifyConstant(method = "onLivingUpdate", constant = @Constant(floatValue = 0.45F))
    public float pre045F(float src) {
        return src * (float) ScaleTypes.EYES.calculate((Entity) (Object) this);
    }
}
