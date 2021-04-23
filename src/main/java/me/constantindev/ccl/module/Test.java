/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Test
# Created by constantin at 21:26, Mär 17 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.module;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.PacketListener;

import java.io.IOException;

public class Test extends Module {
    public Test() {
        super("TestModule", "Poggê", MType.HIDDEN);
    }

    @Override
    public void onEnable() {
        Cornos.minecraft.getNetworkHandler().sendPacket(new Packet<PacketListener>() {
            @Override
            public void read(PacketByteBuf buf) throws IOException {

            }

            @Override
            public void write(PacketByteBuf buf) throws IOException {
                buf.writeInt(0);
            }

            @Override
            public void apply(PacketListener listener) {

            }
        });
        super.onEnable();
    }
}
