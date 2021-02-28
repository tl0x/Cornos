package me.constantindev.ccl.module;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.MType;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MultiOption;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.config.Toggleable;
import me.constantindev.ccl.etc.helper.ClientHelper;
import net.minecraft.util.math.Vec3d;

public class Flight extends Module {
    int counter = 0;

    public Flight() {
        super("Flight", "Allows you to fly", MType.MOVEMENT);
        this.mconf.add(new MultiOption("mode", "vanilla", new String[]{"vanilla", "static"}));
        this.mconf.add(new Toggleable("toggleFast", true));
        this.mconf.add(new Num("speed", 1.0, 30, 0));
    }

    @Override
    public void onExecute() {
        double speed = ((Num) this.mconf.getByName("speed")).getValue();
        if (((Toggleable) this.mconf.getByName("toggleFast")).isEnabled()) {
            if (counter > 10) counter = 0;
            counter++;
        } else counter = 0;
        switch (this.mconf.getByName("mode").value) {
            case "vanilla":
                assert Cornos.minecraft.player != null;
                Cornos.minecraft.player.abilities.flying = !(counter > 9);
                break;
            case "static":

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
            default:
                ClientHelper.sendChat("Invalid flight mode. Please pick one of vanilla, static.");
                this.mconf.getByName("mode").setValue("vanilla");
                break;
        }
        super.onExecute();
    }
}
