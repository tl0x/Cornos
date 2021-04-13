package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.ms.MType;

public class Range extends Module {
    public static Num range = new Num("range", 4.0, 7.0, 0);

    public Range() {
        super("Range", "long arms", MType.COMBAT);
        this.mconf.add(range);
    }
}
