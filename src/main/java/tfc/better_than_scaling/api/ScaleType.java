package tfc.better_than_scaling.api;

import net.minecraft.src.Entity;
import tfc.better_than_scaling.ducks.EntityExtensions;

import java.util.ArrayList;

public class ScaleType {
    public final String name;
    public final ArrayList<ScaleType> affectedBy = new ArrayList<>();

    public ScaleType(String name) {
        this.name = name;
        ScaleTypes.scaleTypes.put(name, this);
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
    }
}
