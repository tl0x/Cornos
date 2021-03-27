package me.constantindev.ccl.etc.base;

import me.constantindev.ccl.etc.config.Keybind;
import me.constantindev.ccl.etc.config.ModuleConfig;
import me.constantindev.ccl.etc.config.Toggleable;
import me.constantindev.ccl.etc.config.Toggleable$1;
import me.constantindev.ccl.etc.ms.MType;

public class Module {
    public final String name;
    public final String description;
    public final ModuleConfig mconf;
    public final MType type;
    public final Toggleable$1 isOn = new Toggleable$1(false);
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
        this.mconf.add(new Toggleable("visible", true));
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

    public void onFastUpdate() {
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
