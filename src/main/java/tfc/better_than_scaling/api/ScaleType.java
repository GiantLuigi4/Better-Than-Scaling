package tfc.better_than_scaling.api;

import net.minecraft.src.*;
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
        ((EntityExtensions) entity).getScaleData().setScale(this, amount);

        EntityTrackerEntry tracker = ((EntityExtensions) entity).getTracker();
        if (tracker != null) {
            tracker.sendPacketToTrackedPlayersAndTrackedEntity(
                    new ScalePacket(
                            entity.entityId, this.name,
                            amount
                    )
            );
        }
    }
}
