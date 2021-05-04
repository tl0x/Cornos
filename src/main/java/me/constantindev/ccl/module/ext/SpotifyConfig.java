package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MConf;
import me.constantindev.ccl.etc.ms.ModuleType;

public class SpotifyConfig extends Module {
    public static MConf.ConfigKey token = new MConf.ConfigKey("token", "0");

    public SpotifyConfig() {
        super("SpotifyConfig", "why", ModuleType.HIDDEN);
        this.mconf.add(token);
    }
}
