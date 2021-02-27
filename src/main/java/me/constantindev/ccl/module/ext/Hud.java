package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.MType;
import me.constantindev.ccl.etc.base.Module;

public class Hud extends Module {
    public Hud() {
        super("HUD", "Will make shit fancy", MType.MISC);
        this.isOn.setState(true);
    }
    // Logic: IngameRenderHook.java
}
