package me.constantindev.ccl.module;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.FakePlayerEntity;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.event.EventHelper;
import me.constantindev.ccl.etc.event.EventType;
import me.constantindev.ccl.etc.event.arg.PacketEvent;
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityPose;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.util.math.Box;

import java.util.Objects;

public class Freecam extends Module {

    public static boolean freecam = false;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private Box box;
    private float speed;
    private FakePlayerEntity fakePlayer;

    public Freecam() {
        super("Freecam", "Become a ghost and leave your body", MType.MISC);
        this.mconf.add(new Num("speed", 1, 2, 0));
        this.setEnabled(false);
        Module parent = this;
        EventHelper.BUS.registerEvent(EventType.ONPACKETSEND, event -> {
            if (!(event instanceof PacketEvent)) {
                return;
            }
            PacketEvent packetEvent = (PacketEvent) event;

            if (parent.isOn.isOn()) {
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
    }

    @Override
    public void onEnable() {
        if (Objects.nonNull(Cornos.minecraft.world) && Objects.nonNull(Cornos.minecraft.player)) {
            ClientPlayerEntity player = Cornos.minecraft.player;
            this.x = player.getX();
            this.y = player.getY();
            this.z = player.getZ();
            this.yaw = player.yaw;
            this.pitch = player.pitch;
            player.setSprinting(false);
            this.box = player.getBoundingBox();
            fakePlayer = new FakePlayerEntity(Cornos.minecraft.world, player.getGameProfile(), true);
            fakePlayer.setProfile(player);
            fakePlayer.spawn();
            freecam = true;
            player.jump();
            player.abilities.flying = true;

        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (Objects.nonNull(Cornos.minecraft.world) && Objects.nonNull(Cornos.minecraft.player)) {
            ClientPlayerEntity player = Cornos.minecraft.player;
            player.setPos(this.x, this.y, this.z);
            player.noClip = false;
            fakePlayer.despawn();
            player.setVelocity(0D, 0D, 0D);
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
        if (Objects.nonNull(Cornos.minecraft.world) && Objects.nonNull(Cornos.minecraft.player) && freecam) {
            Cornos.minecraft.player.abilities.setFlySpeed(this.speed);
            ClientPlayerEntity player = Cornos.minecraft.player;
            player.setVelocity(0D, 0D, 0D);
            player.setOnGround(false);
            player.noClip = true;
            player.fallDistance = 0;
            player.abilities.flying = true;
            if (player.getPose() == EntityPose.SWIMMING) {
                player.setPose(EntityPose.STANDING);
            }
            /*
            if (Cornos.minecraft.currentScreen == null) {
                Vec3d vec3d = player.getRotationVector();
                if (Cornos.minecraft.options.keyJump.isPressed()) {
                    player.setVelocity(0, 1, 0);
                }
                if (Cornos.minecraft.options.keySneak.isPressed()) {
                    player.setVelocity(0, -1, 0);
                }
                if (Cornos.minecraft.options.keyForward.isPressed()) {
                    player.setVelocity(vec3d.x, 0, vec3d.z);
                }
                if (Cornos.minecraft.options.keyBack.isPressed()) {
                    player.setVelocity(-vec3d.x, 0, -vec3d.z);
                }
                if (Cornos.minecraft.options.keyLeft.isPressed()) {
                    player.setVelocity(vec3d.rotateX(player.getMovementDirection().asRotation()).x, 0, vec3d.rotateX(player.getMovementDirection().asRotation()).z);
                }
                if (Cornos.minecraft.options.keyRight.isPressed()) {
                    player.setVelocity(vec3d.rotateX(0.9F).x, 0, vec3d.rotateZ(0.9F).z);
                }
            }*/
        }
        super.onExecute();
    }

    public void onPlayerEntityTick() {
        assert Cornos.minecraft.player != null;
        Cornos.minecraft.player.noClip = true;
    }
}
