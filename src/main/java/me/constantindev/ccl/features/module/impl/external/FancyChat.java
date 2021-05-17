package me.constantindev.ccl.features.module.impl.external;

import me.constantindev.ccl.features.module.Module;
import me.constantindev.ccl.features.module.ModuleType;

public class FancyChat extends Module {
    public FancyChat() {
        super("FancyChat", "makes your messages go fancy", ModuleType.MISC);
    }
}
