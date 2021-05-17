package me.constantindev.ccl.features.module.impl.external;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.config.CConf;
import me.constantindev.ccl.features.module.Module;
import me.constantindev.ccl.features.module.ModuleType;
import org.lwjgl.glfw.GLFW;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", "its fancy", ModuleType.RENDER);
        this.mconf.getByName("keybind").setValue(GLFW.GLFW_KEY_RIGHT_SHIFT + "");
        this.showNotifications = false;
    }

    @Override
    public void onEnable() {
        if (CConf.cg == null) CConf.cg = new me.constantindev.ccl.gui.screen.ClickGUI();
        Cornos.minecraft.openScreen(CConf.cg);
        this.setEnabled(false);
        super.onEnable();
    }
}
