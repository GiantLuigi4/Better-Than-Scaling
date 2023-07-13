package tfc.better_than_scaling.ducks;

import net.minecraft.client.Minecraft;
import net.minecraft.core.HitResult;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.better_than_scaling.api.ScaleType;
import tfc.better_than_scaling.api.ScaleTypes;

public class TestCode {
//    public static HitResult runTrace(EntityLiving instance, double d, float f) {
//        Vec3d vec3d = instance.getPosition(f).addVector(
//                0,
//                -instance.heightOffset + instance.heightOffset * ScaleTypes.EYES.calculate(instance),
//                0
//        );
//        d *= ScaleTypes.REACH.calculate(instance);
//        Vec3d vec3d1 = instance.getLookAngle(f);
//        Vec3d vec3d2 = vec3d.addVector(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d);
//        return instance.world.rayTraceBlocks(vec3d, vec3d2);
//    }
//
//    public static HitResult f3Trace(Minecraft mc, World instance, Vec3d vec3d, Vec3d vec3d1) {
//        float scl = (float) ScaleTypes.THIRD_PERSON.calculate(mc.renderViewEntity);
//        Vec3d vec = vec3d1.addVector(-vec3d.xCoord, -vec3d.yCoord, -vec3d.zCoord);
//        vec = Vec3d.createVector(vec.xCoord * scl, vec.yCoord * scl, vec.zCoord * scl);
//        vec = vec.addVector(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord);
//        HitResult position = instance.rayTraceBlocks(
//                vec3d, vec
//        );
//        if (position == null) {
//            return new HitResult(
//                    0, 0, 0, 0,
//                    vec
//            );
//        }
//        return position;
//    }

    public static void scaleBounds(Entity instance, double x, double y, double z) {
        float scaleX = (float) ScaleTypes.WIDTH.calculate(instance);
        float scaleY = (float) ScaleTypes.HEIGHT.calculate(instance);

        float escl = (float) ScaleTypes.EYES.calculate(instance);

        instance.y = y - instance.heightOffset + instance.heightOffset * escl;

        float center = (instance.bbWidth / 2.0F) * scaleX;
        float heightOfMob = instance.bbHeight;

        instance.bb
                .setBounds(
                        x - (double) center,
                        y - (double) (instance.heightOffset * escl) + (double) (instance.bbHeight * scaleY),
                        z - (double) center,
                        x + (double) center,
                        y - (double) (instance.heightOffset * escl) + (double) (instance.bbHeight * scaleY) + (double) heightOfMob,
                        z + (double) center
                );
    }

    public static float scaleCamera(Entity instance) {
        float scl = (float) ScaleTypes.EYES.calculate(instance);
//        return instance.heightOffset + (1.62f - instance.heightOffset) * scl;
        return instance.heightOffset;
    }

    public static void scaleRender(Entity entity) {
        float scl = (float) ScaleTypes.HEIGHT.calculate(entity);
        float sclX = (float) ScaleTypes.WIDTH.calculate(entity);
        float escl = (float) ScaleTypes.EYES.calculate(entity);
        GL11.glTranslatef(0, entity.heightOffset + (0 - entity.heightOffset) * escl, 0);
        GL11.glScalef(sclX, scl, sclX);
    }

    public static void translateRender(Entity entityliving, double d, double d1, double d2) {
        float scl = (float) ScaleTypes.HEIGHT.calculate(entityliving);
        float sclX = (float) ScaleTypes.WIDTH.calculate(entityliving);
        GL11.glTranslatef((float) d / sclX, (float) d1 / scl, (float) d2 / sclX);
    }

    public static void scaleArrowPos(Entity entity, EntityLiving shooter) {
        float scl = (float) ScaleTypes.EYES.calculate(shooter);
        entity.y = shooter.y + shooter.getHeadHeight();
        ((Entity) (Object) entity).y -= 0.1 * scl;
    }
}
