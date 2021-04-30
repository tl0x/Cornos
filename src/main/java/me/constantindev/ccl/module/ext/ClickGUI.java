package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.ClientConfig;
import me.constantindev.ccl.etc.ms.MType;
import org.lwjgl.glfw.GLFW;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", "Opens a graphical user interface for modules", MType.RENDER);
        this.mconf.getByName("keybind").setValue(GLFW.GLFW_KEY_RIGHT_SHIFT + "");
    }

    @Override
    public void onEnable() {
        if (ClientConfig.cg == null) ClientConfig.cg = new me.constantindev.ccl.gui.ClickGUI();
        Cornos.minecraft.openScreen(ClientConfig.cg);
        super.onEnable();
        this.setEnabled(false);
    }
}
