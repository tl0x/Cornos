/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: PlayerBlockPacket
# Created by constantin at 15:44, Mär 07 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.etc.mod;

import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.util.math.BlockPos;

import java.io.IOException;

public class PlayerBlockPacket implements Packet<ServerPlayPacketListener> {
    private float facingY;
    private short placedBlockDirection;
    private BlockPos position;
    private float facingZ;
    private ItemStack stack;
    private float facingX;

    public PlayerBlockPacket(BlockPos bp, int direction, ItemStack inHand, float facingX, float facingY, float facingZ) {
        this.facingX = facingX;
        this.facingY = facingY;
        this.facingZ = facingZ;
        this.placedBlockDirection = (short) direction;
        this.position = bp;
        this.stack = inHand;
    }

    @Override
    public void read(PacketByteBuf buf) throws IOException {
        this.position = buf.readBlockPos();
        this.placedBlockDirection = buf.readUnsignedByte();
        this.stack = buf.readItemStack();
        this.facingX = buf.readUnsignedByte() / 16.0f;
        this.facingY = buf.readUnsignedByte() / 16.0f;
        this.facingZ = buf.readUnsignedByte() / 16.0f;
    }

    @Override
    public void write(PacketByteBuf packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.position);
        packetBuffer.writeByte(this.placedBlockDirection);
        packetBuffer.writeItemStack(this.stack);
        packetBuffer.writeByte((int) (this.facingX * 16.0f));
        packetBuffer.writeByte((int) (this.facingY * 16.0f));
        packetBuffer.writeByte((int) (this.facingZ * 16.0f));
    }

    @Override
    public void apply(ServerPlayPacketListener listener) {
    }
}
