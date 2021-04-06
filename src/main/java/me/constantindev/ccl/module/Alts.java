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
import me.constantindev.ccl.etc.config.ModuleConfig;
import me.constantindev.ccl.etc.ms.MType;

public class Alts extends Module {
    public static ModuleConfig.ConfigKey k = new ModuleConfig.ConfigKey("list", "0");

    public Alts() {
        super("alts", "you are not supposed to look at this shit", MType.HIDDEN);
        this.mconf.add(k);
    }

    @Override
    public void onEnable() {
        this.setEnabled(false);
        super.onEnable();
    }
}
