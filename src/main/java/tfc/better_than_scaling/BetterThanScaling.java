package tfc.better_than_scaling;

import net.fabricmc.api.ModInitializer;
import tfc.better_than_scaling.net.BulkScalePacket;
import tfc.better_than_scaling.net.PacketRegister;
import tfc.better_than_scaling.net.ScalePacket;

public class BetterThanScaling implements ModInitializer {
    public static final String MOD_ID = "better_than_scaling";

    @Override
    public void onInitialize() {
        PacketRegister.register(ScalePacket.class, true, true);
        PacketRegister.register(BulkScalePacket.class, true, true);
    }
}
