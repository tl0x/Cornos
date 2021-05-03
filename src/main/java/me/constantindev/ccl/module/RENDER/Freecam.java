package me.constantindev.ccl.module.RENDER;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MConfNum;
import me.constantindev.ccl.etc.event.EventHelper;
import me.constantindev.ccl.etc.event.EventType;
import me.constantindev.ccl.etc.event.arg.PacketEvent;
import me.constantindev.ccl.etc.ms.ModuleType;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Perspective;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityPose;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

import java.util.Objects;

public class Freecam extends Module {
    // this shit is excessively documented because even i dont understand how it works

    // options
    MConfNum speed = new MConfNum("speed", 1, 10, 0);
    // start cache
    Vec3d startloc;
    float pitch = 0, yaw = 0;
    OtherClientPlayerEntity clone;

    public Freecam() {
        super("Freecam", "Become a ghost and leave your body", ModuleType.RENDER);
        this.mconf.add(speed);
        Module parent = this;
        // prevent movement packets from sending when module is enabled
        EventHelper.BUS.registerEvent(EventType.ONPACKETSEND, event -> {
            PacketEvent pe = (PacketEvent) event;
            if (pe.packet instanceof PlayerMoveC2SPacket) {
                if (parent.isEnabled()) event.cancel();
            }
        });
    }

    @Override
    public void onEnable() {
        // save cache
        assert Cornos.minecraft.player != null;
        startloc = Cornos.minecraft.player.getPos();
        pitch = Cornos.minecraft.player.pitch;
        yaw = Cornos.minecraft.player.yaw;
        // make player entity that shows where we left off when we started the module
        assert Cornos.minecraft.world != null;
        OtherClientPlayerEntity clone = new OtherClientPlayerEntity(Cornos.minecraft.world, Cornos.minecraft.player.getGameProfile());
        clone.copyPositionAndRotation(Cornos.minecraft.player);
        clone.headYaw = Cornos.minecraft.player.headYaw;
        clone.copyFrom(Cornos.minecraft.player);
        this.clone = clone;
        // spawn the entity
        Cornos.minecraft.world.addEntity(-69, clone);
        // make us fly & disable vanilla flight
        Cornos.minecraft.player.abilities.flying = true;
        Cornos.minecraft.player.abilities.setFlySpeed(0);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        assert Cornos.minecraft.player != null;
        // remove the fake player
        if (clone != null) clone.remove();
        clone = null;
        // place us back where we started
        if (startloc != null) {
            Cornos.minecraft.player.updatePositionAndAngles(startloc.x, startloc.y, startloc.z, yaw, pitch);
        }
        startloc = null;
        yaw = pitch = 0;
        // disable flight & re-enable vanilla flight
        Cornos.minecraft.player.abilities.flying = false;
        Cornos.minecraft.player.abilities.setFlySpeed(0.05f);
        // disable noclip
        Cornos.minecraft.player.noClip = false;
        Objects.requireNonNull(Cornos.minecraft.getCameraEntity()).noClip = false;
        // vel 0
        Cornos.minecraft.player.setVelocity(0, 0, 0);
        super.onDisable();
    }

    @Override
    public void onExecute() {
        // set us flying & keep vanilla flight at a 0
        assert Cornos.minecraft.player != null;
        Cornos.minecraft.player.abilities.flying = true;
        Cornos.minecraft.player.abilities.setFlySpeed(0);
        // set ground to false and fall distance to 0
        Cornos.minecraft.player.setOnGround(false);
        Cornos.minecraft.player.fallDistance = 0;
        // first perspective
        Cornos.minecraft.options.setPerspective(Perspective.FIRST_PERSON);
        Cornos.minecraft.player.setSwimming(false);
        Cornos.minecraft.player.setPose(EntityPose.STANDING);

        // static flight, not much to see here, just moving the player

        GameOptions go = Cornos.minecraft.options;
        float speed = (float) this.speed.getValue() / 5;
        float y = Cornos.minecraft.player.yaw;
        int mx = 0, my = 0, mz = 0;
        if (go.keyJump.isPressed()) my++;
        if (go.keyBack.isPressed()) mz++;
        if (go.keyLeft.isPressed()) mx--;
        if (go.keyRight.isPressed()) mx++;
        if (go.keySneak.isPressed()) my--;
        if (go.keyForward.isPressed()) mz--;
        double ts = speed / 2;
        double s = Math.sin(Math.toRadians(y));
        double c = Math.cos(Math.toRadians(y));
        double nx = ts * mz * s;
        double nz = ts * mz * -c;
        double ny = ts * my;
        nx += ts * mx * -c;
        nz += ts * mx * -s;
        Vec3d nv3 = new Vec3d(nx, ny, nz);
        Vec3d ov3 = Cornos.minecraft.player.getPos();
        Vec3d bruh = ov3.add(nv3);
        Cornos.minecraft.player.updatePosition(bruh.x, bruh.y, bruh.z);
        Cornos.minecraft.player.setVelocity(Vec3d.ZERO);
        super.onExecute();
    }

    @Override
    public void onRender(MatrixStack ms, float td) {
        // enable noclip on onRender() because minecraft is retarded
        assert Cornos.minecraft.player != null;
        Cornos.minecraft.player.noClip = true;
        assert Cornos.minecraft.cameraEntity != null;
        Cornos.minecraft.cameraEntity.noClip = true;
        super.onRender(ms, td);
    }
}
