package me.constantindev.ccl.module;

import me.constantindev.ccl.etc.FakePlayerEntity;
import me.constantindev.ccl.etc.MType;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.event.EventHelper;
import me.constantindev.ccl.etc.event.EventType;
import me.constantindev.ccl.etc.event.arg.PacketEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.util.math.Box;

import java.util.Objects;

public class Freecam extends Module {

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private Box box;
    private float speed;

    public static boolean freecam = false;

    private FakePlayerEntity fakePlayer;

    public Freecam() {
        super("Freecam", "Become a ghost and leave your body",MType.MISC);
        this.mconf.add(new Num("speed", 1, 2, 0));
        EventHelper.BUS.registerEvent(EventType.ONPACKETSEND, event -> {
            if (!(event instanceof PacketEvent)) {
                return;
            }
            PacketEvent packetEvent = (PacketEvent) event;

            if (freecam) {
                if (packetEvent.packet instanceof PlayerActionC2SPacket
                        || packetEvent.packet instanceof PlayerInputC2SPacket
                        || packetEvent.packet instanceof PlayerMoveC2SPacket) {
                    event.cancel();
                }
                if (packetEvent.packet instanceof PlayerInteractBlockC2SPacket
                        || packetEvent.packet instanceof PlayerInteractEntityC2SPacket
                        || packetEvent.packet instanceof PlayerInteractItemC2SPacket) {
                    event.cancel();
                }
            }
        });
        this.setEnabled(false);
    }

    @Override
    public void onEnable() {
        if (Objects.nonNull(MinecraftClient.getInstance().world) && Objects.nonNull(MinecraftClient.getInstance().player)) {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            this.x = player.getX();
            this.y = player.getY();
            this.z = player.getZ();
            this.yaw = player.yaw;
            this.pitch = player.pitch;
            player.setSprinting(false);
            this.box = player.getBoundingBox();
            fakePlayer = new FakePlayerEntity(MinecraftClient.getInstance().world, player.getGameProfile(), true);
            fakePlayer.setProfile(player);
            fakePlayer.spawn();
            freecam = true;
            player.jump();
            player.abilities.flying = true;
            player.abilities.setFlySpeed(this.speed);
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (Objects.nonNull(MinecraftClient.getInstance().world) && Objects.nonNull(MinecraftClient.getInstance().player)) {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            player.setPos(this.x, this.y, this.z);
            player.noClip = false;
            fakePlayer.despawn();
            player.setVelocity(0D,0D,0D);
            player.yaw = this.yaw;
            player.pitch = this.pitch;
            player.setBoundingBox(this.box);
            freecam = false;
            player.abilities.flying = false;
            player.abilities.setFlySpeed(0.05f);
        }
        super.onDisable();
    }

    @Override
    public void onExecute() {
        this.speed = (float) ((Num) this.mconf.getByName("speed")).getValue();
        if (Objects.nonNull(MinecraftClient.getInstance().world) && Objects.nonNull(MinecraftClient.getInstance().player) && freecam) {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            player.setVelocity(0D,0D,0D);
            player.setOnGround(false);
            player.noClip = true;
            player.fallDistance = 0;
            player.abilities.flying = true;
            /*
            if (MinecraftClient.getInstance().currentScreen == null) {
                Vec3d vec3d = player.getRotationVector();
                if (MinecraftClient.getInstance().options.keyJump.isPressed()) {
                    player.setVelocity(0, 1, 0);
                }
                if (MinecraftClient.getInstance().options.keySneak.isPressed()) {
                    player.setVelocity(0, -1, 0);
                }
                if (MinecraftClient.getInstance().options.keyForward.isPressed()) {
                    player.setVelocity(vec3d.x, 0, vec3d.z);
                }
                if (MinecraftClient.getInstance().options.keyBack.isPressed()) {
                    player.setVelocity(-vec3d.x, 0, -vec3d.z);
                }
                if (MinecraftClient.getInstance().options.keyLeft.isPressed()) {
                    player.setVelocity(vec3d.rotateX(player.getMovementDirection().asRotation()).x, 0, vec3d.rotateX(player.getMovementDirection().asRotation()).z);
                }
                if (MinecraftClient.getInstance().options.keyRight.isPressed()) {
                    player.setVelocity(vec3d.rotateX(0.9F).x, 0, vec3d.rotateZ(0.9F).z);
                }
            }*/
        }
        super.onExecute();
    }

    public void onPlayerEntityTick() {
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.noClip = true;
    }
}
