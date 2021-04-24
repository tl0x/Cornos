/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Test
# Created by constantin at 21:26, Mär 17 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.module;

import io.netty.buffer.Unpooled;
import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;

import java.io.IOException;

public class Test extends Module {
    public Test() {
        super("TestModule", "Poggê", MType.HIDDEN);
    }

    @Override
    public void onExecute() {
        net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket p = new VehicleMoveC2SPacket();
        PacketByteBuf pb = new PacketByteBuf(Unpooled.buffer());
        pb.writeDouble(Double.POSITIVE_INFINITY);
        pb.writeDouble(Double.POSITIVE_INFINITY);
        pb.writeDouble(Double.POSITIVE_INFINITY);
        pb.writeFloat(Float.POSITIVE_INFINITY);
        pb.writeFloat(Float.POSITIVE_INFINITY);
        try {
            p.read(pb);
            Cornos.minecraft.getNetworkHandler().sendPacket(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onExecute();

    }
}
