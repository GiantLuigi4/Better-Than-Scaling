package tfc.better_than_scaling.api;

import net.fabricmc.loader.api.FabricLoader;
import tfc.better_than_scaling.ducks.EntityExtensions;

import java.util.ArrayList;
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

    public void merge(ScaleData scaleData) {
        for (ScaleType type : scaleData.scales.keySet()) {
            scales.put(type, scaleData.scales.get(type));
        }
    }

    public String[] getTypes() {
        ArrayList<String> types = new ArrayList<>();
        for (ScaleType type : this.scales.keySet())
            types.add(type.name);
        return types.toArray(new String[0]);
    }
}
