package tfc.better_than_scaling.api;

import tfc.better_than_scaling.ducks.EntityExtensions;

import java.util.HashMap;

public class ScaleData {
    public HashMap<ScaleType, Double> scales = new HashMap<>();

    public double getScale() {
        return ScaleTypes.BASE.calculate(this);
    }

    public double get(ScaleType type) {
        return scales.getOrDefault(type, 1.0);
    }

    public void setScale(ScaleType type, double v) {
        scales.put(type, v);
    }
}
