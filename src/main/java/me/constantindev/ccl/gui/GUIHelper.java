package me.constantindev.ccl.gui;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.util.Identifier;

public class GUIHelper {
    public static TexturedButtonWidget createButton(int w, int x, int y, String text, ButtonWidget.PressAction onExecute) {
        return new TexturedButtonWidget(x, y, w, 20, 0, 0, 20, new Identifier("ccl", "btntexture.png"), onExecute);
    }
}
