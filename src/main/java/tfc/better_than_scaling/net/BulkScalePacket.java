package tfc.better_than_scaling.net;

import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;
import tfc.better_than_scaling.MinecraftCache;
import tfc.better_than_scaling.api.ScaleData;
import tfc.better_than_scaling.api.ScaleType;
import tfc.better_than_scaling.api.ScaleTypes;
import tfc.better_than_scaling.ducks.EntityExtensions;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BulkScalePacket extends Packet {
    int id;
    String[] types;
    double[] scales;

    public BulkScalePacket() {
    }

    public BulkScalePacket(Entity entity) {
        this.id = entity.id;
        EntityExtensions extensions = ((EntityExtensions) entity);
        ScaleData data = extensions.getScaleData();
        this.types = data.getTypes();
        this.scales = new double[types.length];
        for (int i = 0; i < scales.length; i++)
            scales[i] = data.scales.get(ScaleTypes.byName(types[i]));
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream) throws IOException {
        id = dataInputStream.readInt();
        int i = dataInputStream.readInt();
        types = new String[i];
        scales = new double[i];
        for (int i1 = 0; i1 < types.length; i1++) {
            types[i1] = dataInputStream.readUTF();
            scales[i1] = dataInputStream.readDouble();
        }
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(id);
        dataOutputStream.writeInt(types.length);
        for (int i = 0; i < types.length; i++) {
            dataOutputStream.writeUTF(types[i]);
            dataOutputStream.writeDouble(scales[i]);
        }
    }

    @Override
    public void processPacket(NetHandler netHandler) {
        for (Entity entity : MinecraftCache.MINECRAFT.thePlayer.world.getLoadedEntityList()) {
            if (entity.id == id) {
                for (int i = 0; i < types.length; i++) {
                    String type = types[i];
                    double scale = scales[i];
                    ScaleType type1 = ScaleTypes.byName(type);
                    type1.set(entity, scale);
                }
                return;
            }
        }
    }

    @Override
    public int getPacketSize() {
        int len = 4 * 2;
        for (String type : types) len += type.length() + 8;
        return len;
    }
}
