package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.ms.ModuleType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AutoFemboy extends Module {
    public static Map<UUID, Integer> repository = new HashMap<>();

    public AutoFemboy() {
        super("AutoFemboy", "Makes everyone a femboy", ModuleType.FUN);
    }

    @Override
    public void onEnable() {
        //Cornos.minecraft.worldRenderer.reload();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        //Cornos.minecraft.worldRenderer.reload();
        super.onDisable();
    }
}
