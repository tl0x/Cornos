package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.ms.ModuleType;

public class BuildLimit extends Module {
    public BuildLimit() {
        super("BuildLimit", "Allows you to build underneath the block you target", ModuleType.WORLD);
    }
    // Logic: PacketTryUseItemOnBlockHook.java
}
