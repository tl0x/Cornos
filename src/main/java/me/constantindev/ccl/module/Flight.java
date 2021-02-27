package me.constantindev.ccl.module;

import me.constantindev.ccl.etc.MType;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MultiOption;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.config.Toggleable;
import me.constantindev.ccl.etc.helper.ClientHelper;
import net.minecraft.client.MinecraftClient;
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
                assert MinecraftClient.getInstance().player != null;
                MinecraftClient.getInstance().player.abilities.flying = !(counter > 9);
                break;
            case "static":

                assert MinecraftClient.getInstance().player != null;
                Vec3d rot = MinecraftClient.getInstance().player.getRotationVector();
                rot = rot.multiply(speed);
                if (counter > 9) MinecraftClient.getInstance().player.setVelocity(0, -0.1, 0);
                else if (MinecraftClient.getInstance().options.keyForward.isPressed())
                    MinecraftClient.getInstance().player.setVelocity(rot.x, rot.y, rot.z);
                else if (MinecraftClient.getInstance().options.keyBack.isPressed())
                    MinecraftClient.getInstance().player.setVelocity(-rot.x, -rot.y, -rot.z);
                else MinecraftClient.getInstance().player.setVelocity(0, 0, 0);
                break;
            default:
                ClientHelper.sendChat("Invalid flight mode. Please pick one of vanilla, static.");
                this.mconf.getByName("mode").setValue("vanilla");
                break;
        }
        super.onExecute();
    }
}
