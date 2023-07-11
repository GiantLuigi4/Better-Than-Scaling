package tfc.better_than_scaling;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tfc.better_than_scaling.cmd.ScaleCommand;
import turniplabs.halplibe.helper.CommandHelper;


public class BetterThanScaling implements ModInitializer {
    public static final String MOD_ID = "better_than_scaling";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        CommandHelper.createCommand(new ScaleCommand(
                "scale"
        ));
    }
}
