package me.constantindev.ccl.gui;

import com.lukflug.panelstudio.CollapsibleContainer;
import com.lukflug.panelstudio.DraggableContainer;
import com.lukflug.panelstudio.SettingsAnimation;
import com.lukflug.panelstudio.mc16.MinecraftGUI;
import com.lukflug.panelstudio.settings.*;
import com.lukflug.panelstudio.theme.ColorScheme;
import com.lukflug.panelstudio.theme.GameSenseTheme;
import com.lukflug.panelstudio.theme.Theme;
import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.Toggleable;
import me.constantindev.ccl.etc.config.*;
import me.constantindev.ccl.etc.helper.KeyBindManager;
import me.constantindev.ccl.etc.ms.MType;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class ClickGUI extends MinecraftGUI {
    private final GUIInterface guiInterface;
    private final com.lukflug.panelstudio.ClickGUI gui;

    public ClickGUI() {
        guiInterface = new GUIInterface(true) {
            @Override
            protected String getResourcePrefix() {
                return "ccl:gui/";
            }

            @Override
            public void drawString(Point pos, String s, Color c) {
                end();
                Cornos.minecraft.textRenderer.draw(new MatrixStack(), s, pos.x, pos.y, c.getRGB());
                begin();
            }

            @Override
            public int getFontWidth(String s) {
                return Cornos.minecraft.textRenderer.getWidth(s);
            }

            @Override
            public int getFontHeight() {
                return Cornos.minecraft.textRenderer.fontHeight;
            }
        };
        Theme theme = new GameSenseTheme(new ColorScheme() {
            @Override
            public Color getActiveColor() {
                return new Color(255, 255, 255);
            }

            @Override
            public Color getInactiveColor() {
                return new Color(137, 137, 137);
            }

            @Override
            public Color getBackgroundColor() {
                return new Color(107, 160, 124, 128);
            }

            @Override
            public Color getOutlineColor() {
                return new Color(0, 0, 0);
            }

            @Override
            public Color getFontColor() {
                return new Color(0, 0, 0);
            }

            @Override
            public int getOpacity() {
                return 255;
            }
        }, 9, 4, 4);
        gui = new com.lukflug.panelstudio.ClickGUI(guiInterface, context -> {
            //int width = (Cornos).minecraft.textRenderer.getWidth(context.getDescription());
            int height = Cornos.minecraft.textRenderer.fontHeight;
            int wH = Cornos.minecraft.getWindow().getScaledHeight();
            //int wW = (Cornos).minecraft.getWindow().getScaledWidth();
            Cornos.minecraft.textRenderer.draw(new MatrixStack(), context.getDescription(), 1, wH - height - 1, 0xFFFFFFFF);
        });
        int offset = 10;
        for (MType type : MType.ALL) {

            int maxW = Cornos.minecraft.textRenderer.getWidth("antioffhandcrash  ");
            for (Module m : ModuleRegistry.getAll()) {
                if (m.type != type) continue;
                maxW = Math.max(maxW, Cornos.minecraft.textRenderer.getWidth(m.name));
            }
            com.lukflug.panelstudio.DraggableContainer container = new DraggableContainer(type.toString(), null, theme.getContainerRenderer(), new SimpleToggleable(false), new SettingsAnimation(ClientConfig.animSpeed), null, new Point(offset += 100, 50), maxW + (Cornos.minecraft.textRenderer.getWidth(" ") * 2));

            gui.addComponent(container);
            for (Module m : ModuleRegistry.getAll()) {
                if (m.type != type) continue;
                maxW = Math.max(maxW, Cornos.minecraft.textRenderer.getWidth(m.name));
                CollapsibleContainer mc = new CollapsibleContainer(m.name, m.description, theme.getContainerRenderer(), new SimpleToggleable(false), new SettingsAnimation(ClientConfig.animSpeed), m.isOn);
                container.addComponent(mc);
                for (ModuleConfig.ConfigKey kc : m.mconf.config) {
                    maxW = Math.max(maxW, Cornos.minecraft.textRenderer.getWidth(kc.key + ": " + kc.value));
                    if (kc instanceof Toggleable) {
                        BooleanComponent bc = new BooleanComponent(kc.key, null, theme.getComponentRenderer(), new com.lukflug.panelstudio.settings.Toggleable() {
                            @Override
                            public void toggle() {
                                ((Toggleable) kc).toggle();
                            }

                            @Override
                            public boolean isOn() {
                                return ((Toggleable) kc).isEnabled();
                            }
                        });
                        mc.addComponent(bc);
                    } else if (kc instanceof MultiOption) {
                        EnumSetting es = new EnumSetting() {
                            int current = 0;

                            @Override
                            public void increment() {
                                current++;
                                if (current > (((MultiOption) kc).possibleValues.length - 1)) current = 0;
                                kc.setValue(((MultiOption) kc).possibleValues[current]);
                            }

                            @Override
                            public String getValueName() {
                                return ((MultiOption) kc).possibleValues[current];
                            }
                        };
                        EnumComponent ec = new EnumComponent(kc.key, null, theme.getComponentRenderer(), es);
                        mc.addComponent(ec);
                    } else if (kc instanceof Keybind) {
                        KeybindSetting ks = new KeybindSetting() {
                            @Override
                            public int getKey() {
                                return (int) ((Keybind) kc).getValue();
                            }

                            @Override
                            public void setKey(int key) {
                                if (key == 47) kc.setValue(-1 + "");
                                else kc.setValue(key + "");
                                KeyBindManager.reload();
                            }

                            @Override
                            public String getKeyName() {
                                String ret;
                                if (this.getKey() < 0) {
                                    ret = "None";
                                } else {
                                    ret = GLFW.glfwGetKeyName(this.getKey(), this.getKey());
                                }
                                if (ret == null) ret = this.getKey() + "";
                                return ret;
                            }
                        };
                        KeybindComponent kc1 = new KeybindComponent(theme.getComponentRenderer(), ks);
                        mc.addComponent(kc1);
                    } else if (kc instanceof Num) {
                        NumberSetting ns = new NumberSetting() {
                            @Override
                            public double getNumber() {

                                return ((Num) kc).getValue();
                            }

                            @Override
                            public void setNumber(double value) {
                                kc.setValue(value + "");
                            }

                            @Override
                            public double getMaximumValue() {
                                return ((Num) kc).max;
                            }

                            @Override
                            public double getMinimumValue() {
                                return ((Num) kc).min;
                            }

                            @Override
                            public int getPrecision() {
                                return 1;
                            }
                        };
                        NumberComponent nc = new NumberComponent(kc.key, null, theme.getComponentRenderer(), ns, ((Num) kc).min, ((Num) kc).max);
                        mc.addComponent(nc);
                    }
                }
            }
        }
    }

    @Override
    protected com.lukflug.panelstudio.ClickGUI getGUI() {
        return gui;
    }

    @Override
    protected GUIInterface getInterface() {
        return guiInterface;
    }

    @Override
    protected int getScrollSpeed() {
        return 1;
    }
}
