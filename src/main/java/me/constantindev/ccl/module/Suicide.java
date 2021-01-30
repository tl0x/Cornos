package me.constantindev.ccl.module;

import me.constantindev.ccl.etc.base.Module;
import net.minecraft.client.MinecraftClient;

public class Suicide extends Module {
    public Suicide() {
        super("Suicide", "You know what this does");
    }

    @Override
    public void onExecute() {
        new Thread(() -> {
            try {
                assert MinecraftClient.getInstance().player != null;
                MinecraftClient.getInstance().player.setVelocity(0, 5, 0);
                Thread.sleep(500);
                MinecraftClient.getInstance().player.setVelocity(0, -5, 0);
            } catch (Exception ignored) {
            }
        }).start();
        this.isEnabled = false;
        super.onExecute();
    }
}
