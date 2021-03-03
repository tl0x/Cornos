package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.MType;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.config.Toggleable;

public class Hud extends Module {
    public Hud() {
        super("HUD", "Will make shit fancy", MType.MISC);
        this.isOn.setState(true);
        this.mconf.add(new Num("rgbSpeed", 5, 20, 1));
        this.mconf.add(new Toggleable("rgbModList", true));
    }
    // Logic: IngameRenderHook.java
}
