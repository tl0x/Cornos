package me.constantindev.ccl.module;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.Toggleable;
import me.constantindev.ccl.etc.event.EventHelper;
import me.constantindev.ccl.etc.event.EventType;
import me.constantindev.ccl.etc.event.arg.PacketEvent;
import me.constantindev.ccl.etc.ms.MType;
import me.constantindev.ccl.mixin.PlayerMoveC2SPacketAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import java.util.Objects;

public class AntiHunger extends Module {
    MinecraftClient mc = Cornos.minecraft;
    private boolean lastOnGround;
    private boolean sendOnGroundTruePacket;
    private boolean ignorePacket;

    public AntiHunger() {
        super("AntiHunger", "Reduces hunger consumption.", MType.EXPLOIT);
        this.mconf.add(new Toggleable("sprint", true));
        this.mconf.add(new Toggleable("onGround", true));
        Module parent = this;
        EventHelper.BUS.registerEvent(EventType.ONPACKETSEND, event -> {
            if (!parent.isOn.isOn()) return;
            if (ignorePacket) return;
            PacketEvent PE = (PacketEvent) event;
            if (PE.packet instanceof ClientCommandC2SPacket && ((Toggleable) this.mconf.getByName("sprint")).isEnabled()) {
                ClientCommandC2SPacket.Mode mode = ((ClientCommandC2SPacket) PE.packet).getMode();
                if (mode == ClientCommandC2SPacket.Mode.START_SPRINTING || mode == ClientCommandC2SPacket.Mode.STOP_SPRINTING) {
                    event.cancel();
                }
            }
            if (PE.packet instanceof PlayerMoveC2SPacket && ((Toggleable) this.mconf.getByName("onGround")).isEnabled()) {
                assert mc.player != null;
                if (mc.player.isOnGround() && mc.player.fallDistance <= 0.0) {
                    assert mc.interactionManager != null;
                    if (!mc.interactionManager.isBreakingBlock()) {
                        ((PlayerMoveC2SPacketAccessor) PE.packet).setOnGround(false);
                    }
                }
            }
        });
    }

    @Override
    public void onExecute() {
        assert mc.player != null;
        if (mc.player.isOnGround() && !lastOnGround && !sendOnGroundTruePacket) sendOnGroundTruePacket = true;

        if (mc.player.isOnGround() && sendOnGroundTruePacket && ((Toggleable) this.mconf.getByName("onGround")).isEnabled()) {
            ignorePacket = true;
            Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new PlayerMoveC2SPacket(true));
            ignorePacket = false;

            sendOnGroundTruePacket = false;
        }

        lastOnGround = mc.player.isOnGround();
    }
}