/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: AutoSign
# Created by constantin at 02:57, Mär 13 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.features.module.impl.world;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.config.MConfMultiOption;
import me.constantindev.ccl.etc.event.EventHelper;
import me.constantindev.ccl.etc.event.EventType;
import me.constantindev.ccl.etc.event.arg.PacketApplyEvent;
import me.constantindev.ccl.etc.helper.Rnd;
import me.constantindev.ccl.features.module.Module;
import me.constantindev.ccl.features.module.ModuleType;
import net.minecraft.network.packet.c2s.play.UpdateSignC2SPacket;
import net.minecraft.network.packet.s2c.play.SignEditorOpenS2CPacket;

public class AutoSign extends Module {
    public static String line1 = "please run .as";
    public static String line2 = "and configure";
    public static String line3 = "your custom";
    public static String line4 = "text";
    MConfMultiOption type;

    public AutoSign() {
        super("AutoSign", "Writes signs automatically", ModuleType.WORLD);
        type = (MConfMultiOption) this.mconf.add(new MConfMultiOption("mode", "noise", new String[]{"noise", "copypasta", "longlines", "custom"}));
        Module parent = this;
        EventHelper.BUS.registerEvent(EventType.ONPACKETHANDLE, event -> {
            PacketApplyEvent PE = (PacketApplyEvent) event;
            if (PE.packet instanceof SignEditorOpenS2CPacket && parent.isEnabled()) {
                event.cancel();
                if (Cornos.minecraft.getNetworkHandler() == null) return;
                SignEditorOpenS2CPacket p = (SignEditorOpenS2CPacket) PE.packet;
                String[] lines = new String[4];
                switch (type.value) {
                    case "noise":
                        lines = new String[]{
                                Rnd.rndBinStr(15),
                                Rnd.rndBinStr(15),
                                Rnd.rndBinStr(15),
                                Rnd.rndBinStr(15)
                        };
                        break;
                    case "copypasta":
                        lines = new String[]{
                                "cornos is just",
                                "the best client",
                                "to ever exist",
                                "its true"
                        };
                        break;
                    case "longlines":
                        lines = new String[]{
                                Rnd.rndBinStr(50),
                                Rnd.rndBinStr(50),
                                Rnd.rndBinStr(50),
                                Rnd.rndBinStr(50)
                        };
                        break;
                    case "custom":
                        lines = new String[]{line1, line2, line3, line4};
                        break;
                }
                net.minecraft.network.packet.c2s.play.UpdateSignC2SPacket packet = new UpdateSignC2SPacket(p.getPos(), lines[0], lines[1], lines[2], lines[3]);
                Cornos.minecraft.getNetworkHandler().sendPacket(packet);
            }
        });
    }
}
