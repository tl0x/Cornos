package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.MType;
import me.constantindev.ccl.etc.base.Module;

public class BuildLimit extends Module {
    public BuildLimit() {
        super("BuildLimit", "Allows you to build underneath the block you target", MType.WORLD);
    }
    // Logic: PacketTryUseItemOnBlockHook.java
}
