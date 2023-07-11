package tfc.better_than_scaling.mixin.client;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import tfc.better_than_scaling.api.ScaleTypes;

@Mixin(value = EntityPlayerSP.class, remap = false)
public class EntityPlayerSPMixin {
    @Redirect(method = "onLivingUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/src/EntityPlayerSP;width:F"))
    public float redirWidth(EntityPlayerSP instance) {
        return instance.width * (float) ScaleTypes.WIDTH.calculate((Entity) (Object) this);
    }
}
