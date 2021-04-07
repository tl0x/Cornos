package me.constantindev.ccl.module.MOVEMENT;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.Notification;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MultiOption;
import me.constantindev.ccl.etc.event.EventHelper;
import me.constantindev.ccl.etc.event.EventType;
import me.constantindev.ccl.etc.event.arg.PacketEvent;
import me.constantindev.ccl.etc.helper.ClientHelper;
import me.constantindev.ccl.etc.ms.KeyBind;
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.client.options.GameOptions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class BoatFly extends Module {
    Entity lastRide = null;
    KeyBind keyDown = new KeyBind(GLFW.GLFW_KEY_LEFT_ALT);
    MultiOption mode = new MultiOption("mode","vanilla",new String[]{"vanilla","static","velocity"});
    public BoatFly() {
        super("EntityFly", "weee", MType.MOVEMENT);
        mconf.add(mode);
    }

    @Override
    public void onDisable() {
        if (lastRide != null) lastRide.setNoGravity(false);
        super.onDisable();
    }

    @Override
    public void onExecute() {
        Entity vehicle = Cornos.minecraft.player.getVehicle();
        if (vehicle == null) {
            Notification.create("EntityFly", new String[]{"Disabled due to lack","of riding entity"}, 5000);
            this.setEnabled(false);
            return;
        }
        lastRide = vehicle;
        vehicle.setNoGravity(true);
        Vec3d np = vehicle.getPos();
        GameOptions go = Cornos.minecraft.options;

        switch(mode.value) {
            case "vanilla":
                if (go.keyJump.isPressed()) np = np.add(0,.2,0);
                if (keyDown.isHeld()) np = np.add(0,-.2,0);
                vehicle.updatePosition(np.x,np.y,np.z);
                Vec3d v = vehicle.getVelocity();
                Vec3d nv = new Vec3d(v.x,0,v.z);
                vehicle.setVelocity(nv);
                break;
            case "velocity":
            case "static":
                float y = vehicle.yaw;
                int mx = 0, my = 0, mz = 0;
                if (go.keyJump.isPressed()) my++;
                if (go.keyBack.isPressed()) mz++;
                if (go.keyLeft.isPressed()) mx--;
                if (go.keyRight.isPressed()) mx++;
                if (keyDown.isHeld()) my--;
                if (go.keyForward.isPressed()) mz--;
                double ts = 1;
                double s = Math.sin(Math.toRadians(y));
                double c = Math.cos(Math.toRadians(y));
                double nx = ts * mz * s;
                double nz = ts * mz * -c;
                double ny = ts * my;
                nx += ts * mx * -c;
                nz += ts * mx * -s;
                Vec3d nv3 = new Vec3d(nx, ny, nz);
                np = np.add(nv3);
                if (mode.value.equals("static")) {
                    vehicle.updatePosition(np.x,np.y,np.z);
                    vehicle.setVelocity(0,0,0);
                }
                else {
                    nv3 = nv3.multiply(0.1);
                    vehicle.addVelocity(nv3.x,nv3.y,nv3.z);
                }
                break;
        }

        VehicleMoveC2SPacket p = new VehicleMoveC2SPacket(vehicle);
        Cornos.minecraft.getNetworkHandler().sendPacket(p);
        super.onExecute();
    }
}
