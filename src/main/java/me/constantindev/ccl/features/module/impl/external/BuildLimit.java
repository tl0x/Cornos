package me.constantindev.ccl.features.module.impl.external;

import me.constantindev.ccl.features.module.Module;
import me.constantindev.ccl.features.module.ModuleType;

public class BuildLimit extends Module {
    public BuildLimit() {
        super("BuildLimit", "Allows you to build underneath the block you target", ModuleType.WORLD);
    }
    // Logic: PacketTryUseItemOnBlockHook.java
}
