/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: NoRender
# Created by constantin at 19:04, Mär 26 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MConfToggleable;
import me.constantindev.ccl.etc.ms.ModuleType;

public class NoRender extends Module {
    public static MConfToggleable fire = new MConfToggleable("fire", true);
    public static MConfToggleable pumpkin = new MConfToggleable("pumpkin", true);
    public static MConfToggleable sign = new MConfToggleable("signs", false);
    public static MConfToggleable armorstand = new MConfToggleable("armorstands", false);
    public static MConfToggleable particles = new MConfToggleable("particles", false);

    public NoRender() {
        super("NoRender", "Does not render some things", ModuleType.RENDER);
        this.mconf.add(fire);
        this.mconf.add(pumpkin);
        this.mconf.add(sign);
        this.mconf.add(armorstand);
        this.mconf.add(particles);
    }
}
