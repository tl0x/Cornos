package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MConfColor;
import me.constantindev.ccl.etc.config.MConfNum;
import me.constantindev.ccl.etc.config.MConfToggleable;
import me.constantindev.ccl.etc.ms.ModuleType;

import java.awt.*;

public class Hud extends Module {
    public static MConfColor themeColor = new MConfColor("theme", new Color(255, 255, 255));

    public Hud() {
        super("HUD", "Shows stuff on screen ig", ModuleType.RENDER);
        this.setEnabled(true);
        this.mconf.add(new MConfNum("rgbSpeed", 5, 20, 1));
        this.mconf.add(themeColor);
        this.mconf.add(new MConfToggleable("fps", true));
        this.mconf.add(new MConfToggleable("coords", true));
        this.mconf.add(new MConfToggleable("effects", true));
        this.mconf.add(new MConfToggleable("time", true));
        this.mconf.add(new MConfToggleable("tps", true));
        this.mconf.add(new MConfToggleable("graph", true));
        this.mconf.add(new MConfToggleable("ping", true));
        this.mconf.add(new MConfToggleable("modules", true));
        this.mconf.add(new MConfToggleable("miniplayer", true));
        this.mconf.add(new MConfToggleable("taco", false));
        this.mconf.add(new MConfToggleable("dababycar", false));
        themeColor.setRainbow(true);
    }
    // Logic: IngameRenderHook.java
}
