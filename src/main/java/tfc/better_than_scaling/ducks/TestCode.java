package tfc.better_than_scaling.ducks;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.better_than_scaling.api.ScaleType;
import tfc.better_than_scaling.api.ScaleTypes;

public class TestCode {
    public static MovingObjectPosition runTrace(EntityLiving instance, double d, float f) {
        Vec3D vec3d = instance.getPosition(f).addVector(
                0,
                -instance.yOffset + instance.yOffset * ScaleTypes.EYES.calculate(instance),
                0
        );
        d *= ScaleTypes.REACH.calculate(instance);
        Vec3D vec3d1 = instance.getLook(f);
        Vec3D vec3d2 = vec3d.addVector(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d);
        return instance.worldObj.rayTraceBlocks(vec3d, vec3d2);
    }

    public static MovingObjectPosition f3Trace(Minecraft mc, World instance, Vec3D vec3d, Vec3D vec3d1) {
        float scl = (float) ScaleTypes.THIRD_PERSON.calculate(mc.renderViewEntity);
        Vec3D vec = vec3d1.addVector(-vec3d.xCoord, -vec3d.yCoord, -vec3d.zCoord);
        vec = Vec3D.createVector(vec.xCoord * scl, vec.yCoord * scl, vec.zCoord * scl);
        vec = vec.addVector(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord);
        MovingObjectPosition position = instance.rayTraceBlocks(
                vec3d, vec
        );
        if (position == null) {
            return new MovingObjectPosition(
                    0, 0, 0, 0,
                    vec
            );
        }
        return position;
    }

    public static void scaleBounds(Entity instance, double x, double y, double z) {
        float scaleX = (float) ScaleTypes.WIDTH.calculate((Entity) (Object) instance);
        float scaleY = (float) ScaleTypes.HEIGHT.calculate((Entity) (Object) instance);

        float escl = (float) ScaleTypes.EYES.calculate((Entity) (Object) instance);

        instance.posY = y - instance.yOffset + instance.yOffset * escl;

        float center = (instance.width / 2.0F) * scaleX;
        float heightOfMob = instance.height;

        instance.boundingBox
                .setBounds(
                        x - (double) center,
                        y - (double) (instance.yOffset * escl) + (double) (instance.ySize * scaleY),
                        z - (double) center,
                        x + (double) center,
                        y - (double) (instance.yOffset * escl) + (double) (instance.ySize * scaleY) + (double) heightOfMob,
                        z + (double) center
                );
    }

    public static float scaleCamera(Entity instance) {
        float scl = (float) ScaleTypes.EYES.calculate(instance);
//        return instance.yOffset + (1.62f - instance.yOffset) * scl;
        return instance.yOffset;
    }

    public static void scaleRender(Entity entity) {
        float scl = (float) ScaleTypes.HEIGHT.calculate(entity);
        float sclX = (float) ScaleTypes.WIDTH.calculate(entity);
        float escl = (float) ScaleTypes.EYES.calculate(entity);
        GL11.glTranslatef(0, entity.yOffset + (0 - entity.yOffset) * escl, 0);
        GL11.glScalef(sclX, scl, sclX);
    }

    public static void translateRender(Entity entityliving, double d, double d1, double d2) {
        float scl = (float) ScaleTypes.HEIGHT.calculate(entityliving);
        float sclX = (float) ScaleTypes.WIDTH.calculate(entityliving);
        GL11.glTranslatef((float) d / sclX, (float) d1 / scl, (float) d2 / sclX);
    }
}
