/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: NoRender
# Created by constantin at 19:04, Mär 26 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.zeroX150.cornos.features.module.impl.external;

import me.zeroX150.cornos.etc.config.MConfToggleable;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;

public class NoRender extends Module {
    public static MConfToggleable fire = new MConfToggleable("fire", true, "Whether or not to show the fire overlay");
    public static MConfToggleable pumpkin = new MConfToggleable("pumpkin", true, "Whether or not to show the pumpkin overlay");
    public static MConfToggleable sign = new MConfToggleable("signs", false, "Whether or not to render signs");
    public static MConfToggleable armorstand = new MConfToggleable("armorstands", false, "Whether or not to render armor stands");
    public static MConfToggleable particles = new MConfToggleable("particles", true, "Whether or not to render particles");
    public static MConfToggleable maps = new MConfToggleable("maps", true, "Whether or not to render maps");

    public NoRender() {
        super("NoRender", "Doesnt render annoying shit", ModuleType.RENDER);
        this.mconf.add(fire);
        this.mconf.add(pumpkin);
        this.mconf.add(sign);
        this.mconf.add(armorstand);
        this.mconf.add(particles);
        this.mconf.add(maps);
    }
}
