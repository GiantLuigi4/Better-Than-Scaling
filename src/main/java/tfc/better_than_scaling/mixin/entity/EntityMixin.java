package tfc.better_than_scaling.mixin.entity;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityTrackerEntry;
import net.minecraft.src.NBTTagCompound;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.better_than_scaling.api.ScaleData;
import tfc.better_than_scaling.api.ScaleType;
import tfc.better_than_scaling.api.ScaleTypes;
import tfc.better_than_scaling.ducks.EntityExtensions;
import tfc.better_than_scaling.ducks.TestCode;

@Mixin(value = Entity.class, remap = false)
public abstract class EntityMixin implements EntityExtensions {
    ScaleData scaleData = new ScaleData();

    EntityTrackerEntry entityTrackerEntry;

    @Override
    public void setTracker(EntityTrackerEntry lookup) {
        this.entityTrackerEntry = lookup;
    }

    @Override
    public EntityTrackerEntry getTracker() {
        return entityTrackerEntry;
    }

    @Override
    public ScaleData getScaleData() {
        return scaleData;
    }

    @Shadow @Final public AxisAlignedBB boundingBox;

    @Shadow public float height;

    @Shadow public abstract void setPosition(double x, double y, double z);

    @Shadow public double posX;

    @Shadow public double posY;

    @Shadow public double posZ;

    // hitbox scaling
    @Inject(at = @At("TAIL"), method = "setPosition")
    public void postSetPos(double x, double y, double z, CallbackInfo ci) {
        TestCode.scaleBounds((Entity) (Object) this, x, y, z);
    }

    //@formatter:off
    @Redirect(method = "onEntityUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/src/Entity;motionX:D"))
    public double scaleXMot(Entity instance) { return instance.motionX * ScaleTypes.MOTION.calculate((Entity) (Object) this); }
    @Redirect(method = "onEntityUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/src/Entity;motionY:D"))
    public double scaleYMot(Entity instance) { return instance.motionY * ScaleTypes.MOTION.calculate((Entity) (Object) this); }
    @Redirect(method = "onEntityUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/src/Entity;motionZ:D"))
    public double scaleZMot(Entity instance) { return instance.motionZ * ScaleTypes.MOTION.calculate((Entity) (Object) this); }

    // motion scaling
    @ModifyVariable(method = "moveEntity", at = @At("HEAD"),  ordinal = 0, argsOnly = true)
    public double preMoveX(double x) { return x * ScaleTypes.MOTION.calculate((Entity) (Object) this); }
    @ModifyVariable(method = "moveEntity", at = @At("HEAD"),  ordinal = 1, argsOnly = true)
    public double preMoveY(double x) { return x * ScaleTypes.MOTION.calculate((Entity) (Object) this); }
    @ModifyVariable(method = "moveEntity", at = @At("HEAD"),  ordinal = 2, argsOnly = true)
    public double preMoveZ(double x) { return x * ScaleTypes.MOTION.calculate((Entity) (Object) this); }

    // view bobbing
    @ModifyConstant(method = "moveEntity", constant = @Constant(doubleValue = 0.6))
    public double pre06f(double constant) {return constant / ScaleTypes.MOTION.calculate((Entity) (Object) this);}
    //@formatter:on

    @Inject(at = @At("HEAD"), method = "writeToNBT")
    public void preWrite(NBTTagCompound nbttagcompound, CallbackInfo ci) {
        NBTTagCompound compound = new NBTTagCompound();
        scaleData.scales.forEach((entry, value) -> {
            if (value != 1) compound.setDouble(entry.name, value);
        });
        nbttagcompound.setCompoundTag("better_than_scaling", compound);
    }

    @Inject(at = @At("HEAD"), method = "readFromNBT")
    public void preRead(NBTTagCompound nbttagcompound, CallbackInfo ci) {
        NBTTagCompound compound = nbttagcompound.getCompoundTag("better_than_scaling");
        for (ScaleType scaleType : ScaleTypes.getScaleTypes()) {
            if (compound.hasKey(scaleType.name)) {
                scaleData.setScale(scaleType, compound.getDouble(scaleType.name));
            }
        }
    }

    @Redirect(method = "moveEntity", at = @At(value = "FIELD", target = "Lnet/minecraft/src/Entity;yOffset:F"))
    public float calcYOffset1(Entity instance) {
        return instance.yOffset * (float) ScaleTypes.EYES.calculate((Entity) (Object) this);
    }

    @Redirect(method = "moveEntity", at = @At(value = "FIELD", target = "Lnet/minecraft/src/Entity;stepHeight:F"))
    public float calcStepHeight(Entity instance) {
        return instance.stepHeight * (float) ScaleTypes.STEP.calculate((Entity) (Object) this);
    }

    @Redirect(method = "getEntityBrightness", at = @At(value = "FIELD", target = "Lnet/minecraft/src/Entity;yOffset:F"))
    public float calcYOffset3(Entity instance) {
        return instance.yOffset * (float) ScaleTypes.EYES.calculate((Entity) (Object) this);
    }

    @Redirect(method = "isEntityInsideOpaqueBlock", at = @At(value = "FIELD", target = "Lnet/minecraft/src/Entity;width:F"))
    public float calcWidth(Entity instance) {
        return instance.width * (float) ScaleTypes.WIDTH.calculate((Entity) (Object) this);
    }

    @Redirect(method = "handleWaterMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/AxisAlignedBB;expand(DDD)Lnet/minecraft/src/AxisAlignedBB;"))
    public AxisAlignedBB calcOffset(AxisAlignedBB instance, double d, double d1, double d2) {
        return instance.expand(
                d,
                d1 * (float) ScaleTypes.HEIGHT.calculate((Entity) (Object) this),
                d2
        );
    }
}
