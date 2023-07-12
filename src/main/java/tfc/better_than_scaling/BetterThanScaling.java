package tfc.better_than_scaling;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tfc.better_than_scaling.net.BulkScalePacket;
import tfc.better_than_scaling.net.PacketRegister;
import tfc.better_than_scaling.net.ScalePacket;

public class BetterThanScaling implements ModInitializer {
    public static final String MOD_ID = "better_than_scaling";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        PacketRegister.register(ScalePacket.class, false, true);
        PacketRegister.register(BulkScalePacket.class, false, true);
    }
}
