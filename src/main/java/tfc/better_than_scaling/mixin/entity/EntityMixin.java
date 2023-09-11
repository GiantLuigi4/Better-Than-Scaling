package tfc.better_than_scaling.mixin.entity;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.DoubleTag;
import com.mojang.nbt.ListTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityTrackerEntry;
import net.minecraft.core.util.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfc.better_than_scaling.api.ScaleData;
import tfc.better_than_scaling.api.ScaleType;
import tfc.better_than_scaling.api.ScaleTypes;
import tfc.better_than_scaling.ducks.EntityExtensions;
import tfc.better_than_scaling.ducks.TestCode;

@Mixin(value = Entity.class, remap = false)
public abstract class EntityMixin implements EntityExtensions {
    @Shadow
    protected abstract ListTag newDoubleList(double[] array);

    @Shadow
    public double xo;
    @Shadow
    public double yo;
    @Shadow
    public double zo;
    @Shadow
    public double zOld;
    @Shadow
    public double yOld;
    @Shadow
    public double xOld;
    @Shadow
    public double x;
    @Shadow
    public double y;
    @Shadow
    public double z;

    @Shadow
    public abstract void setPos(double x, double y, double z);

    @Shadow
    public float heightOffset;
    @Shadow
    public float ySlideOffset;
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

//    @Inject(at = @At("TAIL"), method = "<init>")
//    public void postInit(World world, CallbackInfo ci) {
//        scaleData.setScale(ScaleTypes.BASE, Math.random() * 1.5 + 0.5);
//    }

    // hitbox scaling
    @Inject(at = @At("TAIL"), method = "setPos")
    public void postSetPos(double x, double y, double z, CallbackInfo ci) {
        TestCode.scaleBounds((Entity) (Object) this, x, y, z);
    }

    //@formatter:off
    @Redirect(method = "baseTick", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/Entity;xd:D"))
    public double scaleXMot(Entity instance) {
        return instance.xd * ScaleTypes.MOTION.calculate((Entity) (Object) this);
    }

    @Redirect(method = "baseTick", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/Entity;yd:D"))
    public double scaleYMot(Entity instance) {
        return instance.yd * ScaleTypes.MOTION.calculate((Entity) (Object) this);
    }

    @Redirect(method = "baseTick", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/Entity;zd:D"))
    public double scaleZMot(Entity instance) {
        return instance.zd * ScaleTypes.MOTION.calculate((Entity) (Object) this);
    }

    // motion scaling
    @ModifyVariable(method = "move", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public double preMoveX(double x) {
        return x * ScaleTypes.MOTION.calculate((Entity) (Object) this);
    }

    @ModifyVariable(method = "move", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    public double preMoveY(double x) {
        return x * ScaleTypes.MOTION.calculate((Entity) (Object) this);
    }

    @ModifyVariable(method = "move", at = @At("HEAD"), ordinal = 2, argsOnly = true)
    public double preMoveZ(double x) {
        return x * ScaleTypes.MOTION.calculate((Entity) (Object) this);
    }

    // view bobbing
    @ModifyConstant(method = "move", constant = @Constant(doubleValue = 0.6))
    public double pre06f(double constant) {
        return constant / ScaleTypes.MOTION.calculate((Entity) (Object) this);
    }
    //@formatter:on

    @Inject(at = @At("TAIL"), method = "saveWithoutId")
    public void preWrite(CompoundTag nbttagcompound, CallbackInfo ci) {
        CompoundTag compound = new CompoundTag();
        scaleData.scales.forEach((entry, value) -> {
            if (value != 1) compound.putDouble(entry.name, value);
        });
        nbttagcompound.putCompound("better_than_scaling", compound);
    }

    @Inject(at = @At("TAIL"), method = "load")
    public void preRead(CompoundTag nbttagcompound, CallbackInfo ci) {
        CompoundTag compound = nbttagcompound.getCompound("better_than_scaling");
        for (ScaleType scaleType : ScaleTypes.getScaleTypes()) {
            if (compound.containsKey(scaleType.name)) {
                scaleData.setScale(scaleType, compound.getDouble(scaleType.name));
            }
        }

        ListTag posTag = nbttagcompound.getList("Pos");
        this.xo = this.xOld = this.x = ((DoubleTag) posTag.tagAt(0)).getValue();
        this.yo = this.yOld = this.y = ((DoubleTag) posTag.tagAt(1)).getValue();
        this.zo = this.zOld = this.z = ((DoubleTag) posTag.tagAt(2)).getValue();

        // correct login position
        this.setPos(this.x, this.y + heightOffset - ySlideOffset + ySlideOffset * ScaleTypes.HEIGHT.calculate((Entity) (Object) this), this.z);
    }

    @Redirect(method = "move", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/Entity;heightOffset:F"))
    public float calcYOffset1(Entity instance) {
        return instance.heightOffset * (float) ScaleTypes.EYES.calculate((Entity) (Object) this);
    }

    @Redirect(method = "move", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/Entity;footSize:F"))
    public float calcStepHeight(Entity instance) {
        return instance.footSize * (float) ScaleTypes.STEP.calculate((Entity) (Object) this);
    }

    @Redirect(method = "getBrightness", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/Entity;heightOffset:F"))
    public float calcYOffset3(Entity instance) {
        return instance.heightOffset * (float) ScaleTypes.EYES.calculate((Entity) (Object) this);
    }

    @Redirect(method = "isInWall", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/Entity;bbWidth:F"))
    public float calcWidth(Entity instance) {
        return instance.bbWidth * (float) ScaleTypes.WIDTH.calculate((Entity) (Object) this);
    }

    @Redirect(method = "checkAndHandleWater", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/util/phys/AABB;expand(DDD)Lnet/minecraft/core/util/phys/AABB;"))
    public AABB calcOffset(AABB instance, double d, double d1, double d2) {
        return instance.expand(
                d,
                d1 * (float) ScaleTypes.HEIGHT.calculate((Entity) (Object) this),
                d2
        );
    }

    @Inject(at = @At("HEAD"), method = "checkInTile", cancellable = true)
    public void preCheckInTile(double d, double d1, double d2, CallbackInfoReturnable<Boolean> cir) {
        // TODO: make this respond to player height instead of cancelling it entirely
        if (ScaleTypes.HEIGHT.calculate((Entity) (Object) this) < 0.85)
            cir.setReturnValue(false);
    }

    @ModifyConstant(method = "isInWall", constant = @Constant(floatValue = 0.1f))
    public float redir01f(float constant) {
        return constant * (float) ScaleTypes.EYES.calculate((Entity) (Object) this);
    }
}
