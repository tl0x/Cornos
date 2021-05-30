package me.zeroX150.cornos.features.module.impl.external;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;

public class FullBright extends Module {

    private double oldgamma;

    public FullBright() {
        super("FullBright", "Tired of using torches?", ModuleType.RENDER);
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
