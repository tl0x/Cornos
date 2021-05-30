package me.zeroX150.cornos.etc.config;

import com.lukflug.panelstudio.settings.NumberSetting;
import me.zeroX150.cornos.etc.render.particles.ConnectingParticles;
import me.zeroX150.cornos.gui.screen.ClickGUI;
import me.zeroX150.cornos.gui.screen.HudElements;
import me.zeroX150.cornos.gui.screen.TabGUI;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.awt.*;

public class CConf {
    public static Block[] xrayBlocks;
    public static NumberSetting animSpeed;
    public static ClickGUI cg;
    public static HudElements hudElements;
    public static TabGUI tabGUI;
    public static int latestRGBVal = 0;
    public static String[][] dict;
    public static boolean checkedForUpdates = false;
    public static ConnectingParticles chatScreenParticles;

    public static void init() {
        xrayBlocks = new Block[]{Blocks.DIAMOND_ORE, Blocks.COAL_ORE, Blocks.EMERALD_ORE, Blocks.GOLD_ORE,
                Blocks.IRON_ORE, Blocks.LAPIS_ORE, Blocks.ANCIENT_DEBRIS, Blocks.REDSTONE_ORE};
        dict = new String[][]{
                /* ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ */
                new String[]{"A", "Ａ"}, new String[]{"B", "Ｂ"}, new String[]{"C", "Ｃ"}, new String[]{"D", "Ｄ"},
                new String[]{"E", "Ｅ"}, new String[]{"F", "Ｆ"}, new String[]{"G", "Ｇ"}, new String[]{"H", "Ｈ"},
                new String[]{"I", "Ｉ"}, new String[]{"J", "Ｊ"}, new String[]{"K", "Ｋ"}, new String[]{"L", "Ｌ"},
                new String[]{"M", "Ｍ"}, new String[]{"N", "Ｎ"}, new String[]{"O", "Ｏ"}, new String[]{"P", "Ｐ"},
                new String[]{"Q", "Ｑ"}, new String[]{"R", "Ｒ"}, new String[]{"S", "Ｓ"}, new String[]{"T", "Ｔ"},
                new String[]{"U", "Ｕ"}, new String[]{"V", "Ｖ"}, new String[]{"W", "Ｗ"}, new String[]{"X", "Ｘ"},
                new String[]{"Y", "Ｙ"}, new String[]{"Z", "Ｚ"}};
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
        hudElements = new HudElements();
        tabGUI = new TabGUI();
    }

    public static Color getRGB() {
        return Color.getHSBColor((float) (((double) (System.currentTimeMillis() % 10000)) / 10000), 0.8f, 1);
    }
}
