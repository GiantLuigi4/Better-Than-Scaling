package tfc.better_than_scaling.ducks;

import net.minecraft.core.entity.EntityTrackerEntry;
import tfc.better_than_scaling.api.ScaleData;

public interface EntityExtensions {
    ScaleData getScaleData();
    void setTracker(EntityTrackerEntry lookup);
    EntityTrackerEntry getTracker();
}
