package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.ms.MType;

public class AutoFemboy extends Module {
    public AutoFemboy() {
        super("AutoFemboy", "Makes everyone a femboy", MType.FUN);
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
}
