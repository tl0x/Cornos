package me.constantindev.ccl.features.module.impl.external;

import me.constantindev.ccl.features.module.Module;
import me.constantindev.ccl.features.module.ModuleType;

public class AntiBlockban extends Module {
    public AntiBlockban() {
        super("AntiBlockBan", "removes the pesky blockbans from your life", ModuleType.RENDER);
    }
}
