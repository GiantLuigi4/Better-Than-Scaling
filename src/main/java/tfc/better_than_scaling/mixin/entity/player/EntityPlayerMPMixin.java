package tfc.better_than_scaling.mixin.entity.player;

import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.core.entity.Entity;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import tfc.better_than_scaling.api.ScaleTypes;

@Mixin(value = EntityPlayerMP.class, remap = false)
public class EntityPlayerMPMixin {
    @ModifyConstant(method = "onLivingUpdate", constant = @Constant(floatValue = 0.45F))
    public float pre045F(float src) {
        return src * (float) ScaleTypes.EYES.calculate((Entity) (Object) this);
    }
}
