package me.constantindev.ccl.etc.base;

import me.constantindev.ccl.etc.config.ModuleConfig;

public class Module {
    public final String name;
    public final String description;
    public final ModuleConfig mconf;
    public boolean isEnabled = false;

    public Module(String n, String d) {
        this.name = n;
        this.description = d;
        this.mconf = new ModuleConfig(this);
        this.mconf.add(new ModuleConfig.ConfigKey("keybind", "-1"));
    }

    public void onExecute() {
    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public final void setEnabled(boolean isEnabled) {
        if (isEnabled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
        this.isEnabled = isEnabled;
    }
}
