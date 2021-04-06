package me.constantindev.ccl.etc.mod;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;

public class IndefiniteChatMessagePacket extends ChatMessageC2SPacket {
    String msg;

    public IndefiniteChatMessagePacket(String msg) {
        this.msg = msg;
    }

    @Override
    public void read(PacketByteBuf buf) {
        this.msg = buf.readString();
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeString(this.msg);
    }

    public void apply(ServerPlayPacketListener serverPlayPacketListener) {
        serverPlayPacketListener.onGameMessage(this);
    }

    public String getChatMessage() {
        return this.msg;
    }
}
