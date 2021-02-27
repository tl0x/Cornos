package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.ClientConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.glfw.GLFW;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", "Opens a graphical user interface for modules");
        this.mconf.getByName("keybind").setValue(GLFW.GLFW_KEY_F6 + "");
    }

    @Override
    public void onEnable() {
        if (ClientConfig.cg == null) ClientConfig.cg = new me.constantindev.ccl.gui.ClickGUI();
        MinecraftClient.getInstance().openScreen((Screen) ClientConfig.cg);
        super.onEnable();
        this.isOn.setState(false);
    }
}
