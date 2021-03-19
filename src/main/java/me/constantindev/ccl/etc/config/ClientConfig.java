package me.constantindev.ccl.etc.config;

import com.lukflug.panelstudio.settings.NumberSetting;
import com.thealtening.auth.TheAlteningAuthentication;
import me.constantindev.ccl.gui.ClickGUI;
import me.constantindev.ccl.gui.TabGUI;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class ClientConfig {
    public static String chatPrefix;
    public static Block[] xrayBlocks;
    public static NumberSetting animSpeed;
    public static ClickGUI cg;
    public static TabGUI tabGUI;
    public static int latestRGBVal = 0;
    public static TheAlteningAuthentication authentication;

    public static void init() {
        chatPrefix = "}";
        xrayBlocks = new Block[]{
                Blocks.DIAMOND_ORE,
                Blocks.COAL_ORE,
                Blocks.EMERALD_ORE,
                Blocks.GOLD_ORE,
                Blocks.IRON_ORE,
                Blocks.LAPIS_ORE,
                Blocks.ANCIENT_DEBRIS,
                Blocks.REDSTONE_ORE
        };
        animSpeed = new NumberSetting() {
            double num = 0.1;

            @Override
            public double getNumber() {
                return num;
            }

            @Override
            public void setNumber(double value) {
                num = value;
            }

            @Override
            public double getMaximumValue() {
                return 100;
            }

            @Override
            public double getMinimumValue() {
                return 0;
            }

            @Override
            public int getPrecision() {
                return 0;
            }
        };
        tabGUI = new TabGUI();
    }
}
