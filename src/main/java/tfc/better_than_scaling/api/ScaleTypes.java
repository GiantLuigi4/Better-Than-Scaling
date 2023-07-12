package tfc.better_than_scaling.api;

import java.lang.reflect.Field;
import java.util.HashMap;

public class ScaleTypes {
    static final HashMap<String, ScaleType> scaleTypes = new HashMap<>();

    public static ScaleType byName(String name) {
        return scaleTypes.get(name);
    }

    public static ScaleType[] getScaleTypes() {
        return scaleTypes.values().toArray(new ScaleType[0]);
    }

    public static final ScaleType BASE = new ScaleType("better_than_scaling:base");
    public static final ScaleType WIDTH = new ScaleType("better_than_scaling:width");
    public static final ScaleType HEIGHT = new ScaleType("better_than_scaling:height");
    public static final ScaleType REACH = new ScaleType("better_than_scaling:reach");
    public static final ScaleType EYES = new ScaleType("better_than_scaling:eyes");
    public static final ScaleType VIEW = new ScaleType("better_than_scaling:view");
    public static final ScaleType MOTION = new ScaleType("better_than_scaling:motion");
    public static final ScaleType STEP = new ScaleType("better_than_scaling:step");
    public static final ScaleType THIRD_PERSON = new ScaleType("better_than_scaling:third_person");

    // TODO
    public static final ScaleType MINING_SPEED = new ScaleType("better_than_scaling:mining_speed");
    public static final ScaleType FALL_DAMAGE = new ScaleType("better_than_scaling:fall_damage");
    public static final ScaleType HEALTH = new ScaleType("better_than_scaling:health");
    public static final ScaleType DEFENSE = new ScaleType("better_than_scaling:defense");

    static {
        try {
            for (Field declaredField : ScaleTypes.class.getDeclaredFields()) {
                if (
                        !declaredField.getName().equals("BASE") &&
                                !declaredField.getName().equals("STEP") &&
                                !declaredField.getName().equals("EYES") &&
                                !declaredField.getName().equals("scaleTypes")
                ) {
                    ScaleType type = (ScaleType) declaredField.get(null);

                    type.affectedBy.add(BASE);
                }
            }
        } catch (Throwable err) {
            throw new RuntimeException(err);
        }

        EYES.affectedBy.add(HEIGHT);
        STEP.affectedBy.add(HEIGHT);
    }
}
