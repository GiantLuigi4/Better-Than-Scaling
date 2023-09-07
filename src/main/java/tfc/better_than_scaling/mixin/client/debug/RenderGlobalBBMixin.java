package tfc.better_than_scaling.mixin.client.debug;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.RenderGlobal;
import net.minecraft.client.render.camera.ICamera;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.better_than_scaling.api.ScaleTypes;

import java.util.List;

@Mixin(value = RenderGlobal.class, remap = false)
public abstract class RenderGlobalBBMixin {
    @Shadow
    private World worldObj;

    @Shadow
    private Minecraft mc;

    @Shadow
    public abstract void drawOutlinedBoundingBox(AABB axisalignedbb);

    @Inject(method = "drawDebugEntityOutlines", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;getEntitiesWithinAABBExcludingEntity(Lnet/minecraft/core/entity/Entity;Lnet/minecraft/core/util/phys/AABB;)Ljava/util/List;", shift = At.Shift.BEFORE), cancellable = true)
    public void preGetEntities(ICamera camera, float partialTicks, CallbackInfo ci) {
        double offsetX = camera.getX(partialTicks);
        double offsetY = camera.getY(partialTicks);
        double offsetZ = camera.getZ(partialTicks);

        float entityRadius = 10.0F;
        if (this.mc.thePlayer.getGamemode() == Gamemode.creative) {
            entityRadius = 100.0F;
        }

        List<Entity> entitiesNearby = this.worldObj.getEntitiesWithinAABB(Entity.class, this.mc.thePlayer.bb.expand(entityRadius, entityRadius, entityRadius));

        for (Entity e : entitiesNearby) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawOutlinedBoundingBox(e.bb.getOffsetBoundingBox(-offsetX, -offsetY, -offsetZ));
            if (e instanceof EntityLiving) {
                float eyes = (float) ScaleTypes.EYES.calculate(e);

                GL11.glColor4f(1.0F, 0.0F, 0.0F, 1.0F);
                EntityLiving living = (EntityLiving) e;

                float h = living.getHeadHeight();
                if (living instanceof EntityPlayer) h = 0;

                this.drawOutlinedBoundingBox(new AABB(
                        e.bb.minX - offsetX,
                        e.y - offsetY + h - 0.1 * eyes,
                        e.bb.minZ - offsetZ,
                        e.bb.maxX - offsetX,
                        e.y - offsetY + h + 0.1 * eyes,
                        e.bb.maxZ - offsetZ
                ));
            }
        }

        GL11.glEnable(3553);

        ci.cancel();
    }
}
