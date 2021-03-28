package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.config.RGBAColor;
import me.constantindev.ccl.etc.config.Toggleable;
import me.constantindev.ccl.etc.ms.MType;
import me.constantindev.ccl.gui.HudElements;

import java.awt.*;

public class Hud extends Module {
    public static RGBAColor themeColor = new RGBAColor("theme", new Color(255, 255, 255));

    public Hud() {
        super("HUD", "Will make shit fancy", MType.RENDER);
        this.isOn.setState(true);
        this.mconf.add(new Num("rgbSpeed", 5, 20, 1));
        this.mconf.add(themeColor);
        this.mconf.add(new Toggleable("fps", true));
        this.mconf.add(new Toggleable("coords", true));
        this.mconf.add(new Toggleable("effects", true));
        this.mconf.add(new Toggleable("time", true));
        this.mconf.add(new Toggleable("tps", true));
        this.mconf.add(new Toggleable("ping", true));
        this.mconf.add(new Toggleable("modules", true));
        this.mconf.add(new Toggleable("miniplayer", true));
        themeColor.setRainbow(true);
    }
    // Logic: IngameRenderHook.java
}
