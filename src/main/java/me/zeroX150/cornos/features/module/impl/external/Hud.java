package me.zeroX150.cornos.features.module.impl.external;

import me.zeroX150.cornos.etc.config.MConfColor;
import me.zeroX150.cornos.etc.config.MConfNum;
import me.zeroX150.cornos.etc.config.MConfToggleable;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;

import java.awt.*;

public class Hud extends Module {
    public static MConfColor themeColor = new MConfColor("theme", new Color(255, 255, 255), "The theme color");
    public static MConfNum modulesRgbScale = new MConfNum("rgbScale", 0.03, 0.3, 0.01, "The scale of a rgb segment in the module list (bigger = more rgb iterations)");

    public Hud() {
        super("HUD", "Shows stuff on screen ig", ModuleType.RENDER);
        this.setEnabledWithoutUpdate(true);
        this.mconf.add(new MConfNum("rgbSpeed", 5, 20, 1, "Speed to change rgb values"));
        this.mconf.add(modulesRgbScale);
        this.mconf.add(themeColor);
        this.mconf.add(new MConfToggleable("fps", true, "Whether or not to show your fps"));
        this.mconf.add(new MConfToggleable("coords", true, "Whether or not to show your XYZ"));
        this.mconf.add(new MConfToggleable("effects", true, "Whether or not to show your player effects"));
        this.mconf.add(new MConfToggleable("time", true, "Whether or not to show the current time"));
        this.mconf.add(new MConfToggleable("tps", true, "Whether or not to show server TPS"));
        this.mconf.add(new MConfToggleable("ping", true, "Whether or not to show your ping"));
        this.mconf.add(new MConfToggleable("speedBPS", true, "Whether or not to show your speed in BPS"));
        this.mconf.add(new MConfToggleable("graph", true, "Whether or not to show a graph of the server's TPS"));
        this.mconf.add(new MConfToggleable("modules", true, "Whether or not to show the enabled modules list"));
        this.mconf.add(new MConfToggleable("miniplayer", true, "Whether or not to show a little rendering of your player"));
        this.mconf.add(new MConfToggleable("taco", false, "taco."));
        this.mconf.add(new MConfToggleable("dababycar", false, "less gooo"));
        themeColor.setRainbow(true);
    }
    // Logic: IngameRenderHook.java
}
