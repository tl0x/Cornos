package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MConfNum;
import me.constantindev.ccl.etc.ms.ModuleType;

public class Range extends Module {
    public static MConfNum range = new MConfNum("range", 4.0, 7.0, 0);

    public Range() {
        super("Range", "long arms", ModuleType.COMBAT);
        this.mconf.add(range);
    }
}
