package me.constantindev.ccl.etc.base;

import me.constantindev.ccl.etc.MType;
import me.constantindev.ccl.etc.Toggleable;
import me.constantindev.ccl.etc.config.Keybind;
import me.constantindev.ccl.etc.config.ModuleConfig;

public class Module {
    public final String name;
    public final String description;
    public final ModuleConfig mconf;
    public final MType type;
    public final Toggleable isOn = new Toggleable(false);
    boolean calledVitalsOnenable = true;
    boolean calledVitalsOndisable = true;

    public Module(String n, String d) {
        this(n, d, MType.MISC);
    }

    public Module(String n, String d, MType type) {
        this.name = n;
        this.description = d;
        this.type = type;
        this.mconf = new ModuleConfig(this);
        this.mconf.add(new Keybind("keybind", -1));
    }

    public final void updateVitals() {
        if (this.isOn.isOn()) {
            this.calledVitalsOndisable = false;
            if (!this.calledVitalsOnenable) {
                this.onEnable();
                this.calledVitalsOnenable = true;
            }
        } else {
            this.calledVitalsOnenable = false;
            if (!this.calledVitalsOndisable) {
                this.onDisable();
                this.calledVitalsOndisable = true;
            }
        }
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
        this.isOn.setState(isEnabled);
    }
}
