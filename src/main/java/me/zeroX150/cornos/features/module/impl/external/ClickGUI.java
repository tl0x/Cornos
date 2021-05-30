package me.zeroX150.cornos.features.module.impl.external;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.config.CConf;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;
import org.lwjgl.glfw.GLFW;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", "its fancy", ModuleType.RENDER);
        this.mconf.getByName("keybind").setValue(GLFW.GLFW_KEY_RIGHT_SHIFT + "");
        this.showNotifications = false;
    }

    @Override
    public void onEnable() {
        if (CConf.cg == null)
            CConf.cg = new me.zeroX150.cornos.gui.screen.ClickGUI();
        Cornos.minecraft.openScreen(CConf.cg);
        this.setEnabled(false);
        super.onEnable();
    }
}
