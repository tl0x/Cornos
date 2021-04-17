package me.constantindev.ccl.etc.base;

import me.constantindev.ccl.etc.Notification;
import me.constantindev.ccl.etc.config.Keybind;
import me.constantindev.ccl.etc.config.ModuleConfig;
import me.constantindev.ccl.etc.config.Toggleable;
import me.constantindev.ccl.etc.config.Toggleable$1;
import me.constantindev.ccl.etc.helper.RenderHelper;
import me.constantindev.ccl.etc.ms.MType;
import me.constantindev.ccl.etc.render.RenderableBlock;
import me.constantindev.ccl.etc.render.RenderableLine;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;

public class Module {
    public final String name;
    public final String description;
    public final ModuleConfig mconf;
    public final MType type;
    public final Toggleable$1 isOn = new Toggleable$1(false);
    public boolean showNotifications = true;
    public List<RenderableBlock> rbq = new ArrayList<>();
    public List<RenderableLine> rlq = new ArrayList<>();
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
                if (showNotifications)
                    Notification.create("Module toggle", new String[]{"§aEnabled§r " + this.name}, 1000);
            }
        } else {
            this.calledVitalsOnenable = false;
            if (!this.calledVitalsOndisable) {
                this.onDisable();
                if (showNotifications)
                    Notification.create("Module toggle", new String[]{"§cDisabled§r " + this.name}, 1000);
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

    public void onHudRender(MatrixStack ms, float td) {

    }

    public void onRender(MatrixStack ms, float td) {
        for (RenderableLine rl : rlq.toArray(new RenderableLine[0])) {
            RenderHelper.renderLine(rl.bp1, rl.bp2, rl.c, rl.width);
        }
        for (RenderableBlock rl : rbq.toArray(new RenderableBlock[0])) {
            RenderHelper.renderBlockOutline(rl.bp, rl.dimensions, rl.r, rl.g, rl.b, rl.a);
        }
        rlq.clear();
        rbq.clear();
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
