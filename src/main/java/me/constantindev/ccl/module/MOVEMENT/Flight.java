package me.constantindev.ccl.module.MOVEMENT;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MultiOption;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.config.Toggleable;
import me.constantindev.ccl.etc.helper.RandomHelper;
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.client.options.GameOptions;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.network.packet.c2s.play.UpdatePlayerAbilitiesC2SPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

import java.util.Objects;

public class Flight extends Module {
    int counter = 0;
    int counter1 = 0;
    float flyingSince = 0;
    MultiOption mode = new MultiOption("mode", "vanilla", new String[]{"vanilla", "3d", "static", "jetpack"});
    Toggleable toggleFast = new Toggleable("toggleFast", true);
    Num speed = new Num("speed", 1.0, 10, 0);
    Toggleable sendAbilitiesUpdate = new Toggleable("abilities", true);
    PlayerAbilities abilitiesBefore = null;

    public Flight() {
        super("Flight", "Allows you to fly", MType.MOVEMENT);
        this.mconf.add(mode);
        this.mconf.add(toggleFast);
        this.mconf.add(sendAbilitiesUpdate);
        this.mconf.add(speed);

    }

    @Override
    public void onExecute() {
        if (abilitiesBefore == null) {
            assert Cornos.minecraft.player != null;
            abilitiesBefore = Cornos.minecraft.player.abilities;
        }
        double speed = ((Num) this.mconf.getByName("speed")).getValue();
        counter1++;
        if (counter1 > 10) {
            counter1 = 0;
            assert Cornos.minecraft.player != null;
            PlayerAbilities pa = Cornos.minecraft.player.abilities;
            pa.allowFlying = true;
            UpdatePlayerAbilitiesC2SPacket p = new UpdatePlayerAbilitiesC2SPacket(pa);
            Objects.requireNonNull(Cornos.minecraft.getNetworkHandler()).sendPacket(p);
        }
        if (((Toggleable) this.mconf.getByName("toggleFast")).isEnabled()) {
            if (counter > 10) counter = 0;
            counter++;
        } else counter = 0;
        switch (this.mconf.getByName("mode").value) {
            case "vanilla":
                assert Cornos.minecraft.player != null;
                Cornos.minecraft.player.abilities.flying = !(counter > 9);
                Cornos.minecraft.player.abilities.setFlySpeed((float) (speed / 10));
                break;
            case "3d":
                assert Cornos.minecraft.player != null;
                Vec3d rot = Cornos.minecraft.player.getRotationVector();
                rot = rot.multiply(speed);
                if (counter > 9) Cornos.minecraft.player.setVelocity(0, -0.1, 0);
                else if (Cornos.minecraft.options.keyForward.isPressed())
                    Cornos.minecraft.player.setVelocity(rot.x, rot.y, rot.z);
                else if (Cornos.minecraft.options.keyBack.isPressed())
                    Cornos.minecraft.player.setVelocity(-rot.x, -rot.y, -rot.z);
                else Cornos.minecraft.player.setVelocity(0, 0, 0);
                break;
            case "static":
                float y = Cornos.minecraft.player.yaw;
                int mx = 0, my = 0, mz = 0;
                GameOptions go = Cornos.minecraft.options;
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
                Cornos.minecraft.player.setVelocity(nv3);
                break;
            case "jetpack":
                if (Cornos.minecraft.world == null) return;
                if (Cornos.minecraft.options.keyJump.isPressed()) {
                    assert Cornos.minecraft.player != null;
                    Cornos.minecraft.player.addVelocity(0, speed / 30, 0);
                    Vec3d vp = Cornos.minecraft.player.getPos();
                    for (int i = 0; i < 10; i++) {
                        Cornos.minecraft.world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, vp.x, vp.y, vp.z, RandomHelper.rndD(.25) - .125, RandomHelper.rndD(.25) - .125, RandomHelper.rndD(.25) - .125);
                    }
                }
                break;
        }
        super.onExecute();
    }

    @Override
    public void onEnable() {
        assert Cornos.minecraft.player != null;
        abilitiesBefore = Cornos.minecraft.player.abilities;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        flyingSince = 0;
        if (abilitiesBefore == null) return;
        assert Cornos.minecraft.player != null;
        Cornos.minecraft.player.abilities.setFlySpeed(abilitiesBefore.getFlySpeed());
        Cornos.minecraft.player.abilities.allowFlying = abilitiesBefore.allowFlying;
        Cornos.minecraft.player.abilities.flying = false;
        super.onDisable();
    }
}
