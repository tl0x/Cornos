package me.constantindev.ccl.module;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.ms.MType;

public class FullBright extends Module {

    private double oldgamma;

    public FullBright() {
        super("FullBright", "Light up your world.", MType.MISC);
    }

    @Override
    public void onEnable() {
        oldgamma = Cornos.minecraft.options.gamma;
        Cornos.minecraft.options.gamma = 10D;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        Cornos.minecraft.options.gamma = oldgamma;
        super.onDisable();
    }
}