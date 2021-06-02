package me.zeroX150.cornos.features.module.impl.external;

import me.zeroX150.cornos.etc.config.MConfNum;
import me.zeroX150.cornos.etc.helper.STL;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;

public class Range extends Module {
    public static MConfNum range = new MConfNum("range", 4.0, 7.0, 0, "self explanatory");

    public Range() {
        super("Range", "long arms", ModuleType.COMBAT);
        this.mconf.add(range);
    }

    @Override
    public String getContext() {
        return STL.roundToNTh(range.getValue(), 2) + "";
    }
}
