/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Alts
# Created by constantin at 18:08, Mär 21 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.module;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MConf;
import me.constantindev.ccl.etc.ms.ModuleType;

public class Alts extends Module {
    public static MConf.ConfigKey k = new MConf.ConfigKey("list", "0");

    public Alts() {
        super("alts", "you are not supposed to look at this shit", ModuleType.HIDDEN);
        this.mconf.add(k);
    }

    @Override
    public void onEnable() {
        this.setEnabled(false);
        super.onEnable();
    }
}
