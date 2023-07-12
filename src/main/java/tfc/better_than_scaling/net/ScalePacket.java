package tfc.better_than_scaling.net;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Entity;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;
import tfc.better_than_scaling.api.ScaleType;
import tfc.better_than_scaling.api.ScaleTypes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ScalePacket extends Packet {
    int id;
    String type;
    double scale;

    public ScalePacket() {
    }

    public ScalePacket(int id, String type, double scale) {
        this.id = id;
        this.type = type;
        this.scale = scale;
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream) throws IOException {
        id = dataInputStream.readInt();
        type = dataInputStream.readUTF();
        scale = dataInputStream.readDouble();
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(id);
        dataOutputStream.writeUTF(type);
        dataOutputStream.writeDouble(scale);
    }

    @Override
    public void processPacket(NetHandler netHandler) {
        for (Entity entity : Minecraft.getMinecraft().thePlayer.worldObj.getLoadedEntityList()) {
            if (entity.entityId == id) {
                ScaleType type1 = ScaleTypes.byName(type);
                type1.set(entity, scale);
                return;
            }
        }
    }

    @Override
    public int getPacketSize() {
        return type.length() + 4 + 8;
    }
}
