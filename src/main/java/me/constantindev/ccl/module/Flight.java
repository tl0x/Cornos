package me.constantindev.ccl.module;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.ModuleConfig;
import me.constantindev.ccl.etc.helper.ClientHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

public class Flight extends Module {
    int counter = 0;

    public Flight() {
        super("Flight", "Allows you to fly");
        this.mconf.add(new ModuleConfig.ConfigKey("mode", "vanilla"));
        this.mconf.add(new ModuleConfig.ConfigKey("toggleFast", "on"));
    }

    @Override
    public void onExecute() {
        double speed = 1.0;
        try {
            speed = Double.parseDouble(this.mconf.getOrDefault("speed", new ModuleConfig.ConfigKey("speed", "1.0")).value);
        } catch (Exception exc) {
            this.mconf.getByName("speed").setValue("1.0");
        }
        switch (this.mconf.getByName("toggleFast").value) {
            case "on":
                if (counter > 10) counter = 0;
                counter++;

                break;
            case "off":
                counter = 0;
                break;
            default:
                this.mconf.getByName("toggleFast").setValue("on");
        }
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
