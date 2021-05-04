package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.CConf;
import me.constantindev.ccl.etc.ms.ModuleType;
import org.lwjgl.glfw.GLFW;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", "Opens a graphical user interface for modules", ModuleType.RENDER);
        this.mconf.getByName("keybind").setValue(GLFW.GLFW_KEY_RIGHT_SHIFT + "");
    }

    @Override
    public void onEnable() {
        if (CConf.cg == null) CConf.cg = new me.constantindev.ccl.gui.ClickGUI();
        Cornos.minecraft.openScreen(CConf.cg);
        this.setEnabled(false);
        super.onEnable();
    }
}
