package tfc.better_than_scaling.net;

import net.minecraft.src.Packet;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class PacketRegister {
    public static void register(Class<? extends Packet> packet, boolean server, boolean client) {
        try {
            Field f = Packet.class.getDeclaredField("packetIdToClassMap");
            f.setAccessible(true);

            Map<Integer, Class<?>> map = (Map<Integer, Class<?>>) f.get(null);
            int free = 0;
            for (Integer integer : map.keySet())
                if (free == integer)
                    free = integer + 1;

            Method m = Packet.class.getDeclaredMethod("addIdClassMapping", int.class, boolean.class, boolean.class, Class.class);
            m.setAccessible(true);

            m.invoke(null, free, client, server, packet);
        } catch (Throwable ignored) {
        }
    }
}
