package me.constantindev.ccl.module;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.MType;
import me.constantindev.ccl.etc.base.Module;

public class Suicide extends Module {
    public Suicide() {
        super("Suicide", "You know what this does", MType.EXPLOIT);
    }

    @Override
    public void onExecute() {
        new Thread(() -> {
            try {
                assert Cornos.minecraft.player != null;
                Cornos.minecraft.player.setVelocity(0, 5, 0);
                Thread.sleep(500);
                Cornos.minecraft.player.setVelocity(0, -10, 0);
            } catch (Exception ignored) {
            }
        }).start();
        this.isOn.setState(false);
        super.onExecute();
    }
}
