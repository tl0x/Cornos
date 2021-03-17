package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.config.RGBAColor;
import me.constantindev.ccl.etc.ms.MType;

import java.awt.*;

public class Hud extends Module {
    public static RGBAColor themeColor = new RGBAColor("theme", new Color(255, 255, 255));

    public Hud() {
        super("HUD", "Will make shit fancy", MType.MISC);
        this.isOn.setState(true);
        this.mconf.add(new Num("rgbSpeed", 5, 20, 1));
        this.mconf.add(themeColor);
        themeColor.setRainbow(true);
    }
    // Logic: IngameRenderHook.java
}
