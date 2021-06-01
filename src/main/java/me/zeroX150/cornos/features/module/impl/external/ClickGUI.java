package me.zeroX150.cornos.features.module.impl.external;

import com.lukflug.panelstudio.settings.NumberSetting;
import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.config.CConf;
import me.zeroX150.cornos.etc.config.Colors;
import me.zeroX150.cornos.etc.config.MConfColor;
import me.zeroX150.cornos.etc.config.MConfNum;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;
import org.lwjgl.glfw.GLFW;

public class ClickGUI extends Module {
    public static MConfColor activeColor = new MConfColor("activeColor", Colors.ACTIVE.get(), "The active color");
    public static MConfColor inactiveColor = new MConfColor("inactiveColor", Colors.INACTIVE.get(), "The inactive color");
    public static MConfColor backgroundColor = new MConfColor("backgroundColor", Colors.BACKGROUND.get(), "The background color");
    public static MConfNum animationDuration = new MConfNum("animation", 700, 5000, 0, "The animation duration");

    public ClickGUI() {
        super("ClickGUI", "its fancy", ModuleType.RENDER);
        this.mconf.getByName("keybind").setValue(GLFW.GLFW_KEY_RIGHT_SHIFT + "");
        this.showNotifications = false;
        mconf.add(activeColor);
        mconf.add(inactiveColor);
        mconf.add(backgroundColor);
        mconf.add(animationDuration);
    }

    public static NumberSetting getNumSet() {
        return new NumberSetting() {
            @Override
            public double getNumber() {
                return animationDuration.getValue();
            }

            @Override
            public void setNumber(double value) {
                // do nothing
            }

            @Override
            public double getMaximumValue() {
                return animationDuration.getValue();
            }

            @Override
            public double getMinimumValue() {
                return 0;
            }

            @Override
            public int getPrecision() {
                return 1;
            }
        };
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
