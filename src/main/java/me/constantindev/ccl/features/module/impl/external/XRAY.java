package me.constantindev.ccl.features.module.impl.external;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.features.module.Module;
import me.constantindev.ccl.features.module.ModuleType;

public class XRAY extends Module {
    public XRAY() {
        super("XRAY", "Tired of stripmining?", ModuleType.WORLD);
    }

    @Override
    public void onEnable() {
        Cornos.minecraft.worldRenderer.reload();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        Cornos.minecraft.worldRenderer.reload();
        super.onDisable();
    }
    // Logic: XrayHandler.java & AbstractBlockStateHook.java

}
