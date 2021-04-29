package me.constantindev.ccl.module.COMBAT;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MultiOption;
import me.constantindev.ccl.etc.event.EventHelper;
import me.constantindev.ccl.etc.event.EventType;
import me.constantindev.ccl.etc.event.arg.PacketEvent;
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

public class Criticals extends Module {
    MultiOption mode = new MultiOption("mode", "packet", new String[]{"packet", "tphop", "visual"});

    public Criticals() {
        super("Criticals", "more damage", MType.COMBAT);
        this.mconf.add(mode);
        Module parent = this;
        EventHelper.BUS.registerEvent(EventType.ONPACKETSEND, event -> {
            PacketEvent pe = (PacketEvent) event;
            if (parent.isOn.isOn() && (pe.packet instanceof PlayerInteractEntityC2SPacket)) {
                PlayerInteractEntityC2SPacket p = (PlayerInteractEntityC2SPacket) pe.packet;
                Vec3d pos = Cornos.minecraft.player.getPos();
                switch (mode.value) {
                    case "packet":
                        PlayerMoveC2SPacket.PositionOnly mp = new PlayerMoveC2SPacket.PositionOnly(pos.x, pos.y + 0.2, pos.z, true);
                        PlayerMoveC2SPacket.PositionOnly mp1 = new PlayerMoveC2SPacket.PositionOnly(pos.x, pos.y, pos.z, false);
                        PlayerMoveC2SPacket.PositionOnly mp2 = new PlayerMoveC2SPacket.PositionOnly(pos.x, pos.y + 0.000011, pos.z, false);
                        PlayerMoveC2SPacket.PositionOnly mp3 = new PlayerMoveC2SPacket.PositionOnly(pos.x, pos.y, pos.z, false);
                        Cornos.minecraft.getNetworkHandler().sendPacket(mp);
                        Cornos.minecraft.getNetworkHandler().sendPacket(mp1);
                        Cornos.minecraft.getNetworkHandler().sendPacket(mp2);
                        Cornos.minecraft.getNetworkHandler().sendPacket(mp3);
                        break;
                    case "tphop":
                        PlayerMoveC2SPacket.PositionOnly mp4 = new PlayerMoveC2SPacket.PositionOnly(pos.x, pos.y + 0.02, pos.z, false);
                        PlayerMoveC2SPacket.PositionOnly mp5 = new PlayerMoveC2SPacket.PositionOnly(pos.x, pos.y + 0.01, pos.z, false);
                        Cornos.minecraft.getNetworkHandler().sendPacket(mp4);
                        Cornos.minecraft.getNetworkHandler().sendPacket(mp5);
                        break;
                }
                Cornos.minecraft.player.addCritParticles(p.getEntity(Cornos.minecraft.world));
            }
        });
    }
}
