package me.constantindev.ccl.features.module.impl.external;

import me.constantindev.ccl.etc.config.MConfNum;
import me.constantindev.ccl.features.module.Module;
import me.constantindev.ccl.features.module.ModuleType;

public class Range extends Module {
    public static MConfNum range = new MConfNum("range", 4.0, 7.0, 0);

    public Range() {
        super("Range", "long arms", ModuleType.COMBAT);
        this.mconf.add(range);
    }
}
