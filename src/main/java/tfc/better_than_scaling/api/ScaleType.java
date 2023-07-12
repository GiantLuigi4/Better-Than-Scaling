package tfc.better_than_scaling.api;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityTrackerEntry;
import tfc.better_than_scaling.ducks.EntityExtensions;
import tfc.better_than_scaling.net.ScalePacket;

import java.util.ArrayList;

public class ScaleType {
    public final String name;
    public final ArrayList<ScaleType> affectedBy = new ArrayList<>();

    public ScaleType(String name) {
        this.name = name;
        synchronized (ScaleTypes.scaleTypes) {
            if (ScaleTypes.scaleTypes.containsKey(name))
                throw new RuntimeException("Creating a scale type that has already been created should not be done.");

            ScaleTypes.scaleTypes.put(name, this);
        }
    }

    public double calculate(ScaleData data) {
        double d = data.get(this);
        for (ScaleType type : affectedBy) d *= type.calculate(data);
        return d;
    }

    public final double calculate(Entity entity) {
        return calculate(((EntityExtensions) entity).getScaleData());
    }

    public void set(Entity entity, double amount) {
        double oldWidth = ScaleTypes.WIDTH.calculate(entity);
        double oldHeight = ScaleTypes.HEIGHT.calculate(entity);

        double scl = entity.heightOffset * ScaleTypes.EYES.calculate(entity);
        double oy = entity.y - scl;
        double x = entity.x, y = entity.y - scl, z = entity.z;

        ((EntityExtensions) entity).getScaleData().setScale(this, amount);

        double width = ScaleTypes.WIDTH.calculate(entity);
        double height = ScaleTypes.HEIGHT.calculate(entity);
        // update hitbox if necessary
        // TODO: when growing, the player moves upwards
        if (height != oldHeight || width != oldWidth)
            entity.setPos(x, y, z);

        // account for imprecision
        double ny = entity.y - entity.heightOffset * ScaleTypes.EYES.calculate(entity);
        if (oy > ny) entity.move(0, 0.05, 0);

        // sync to clients
        EntityTrackerEntry tracker = ((EntityExtensions) entity).getTracker();
        if (tracker != null) {
            tracker.sendPacketToTrackedPlayersAndTrackedEntity(
                    new ScalePacket(
                            entity.id, this.name,
                            amount
                    )
            );
        }
    }
}
