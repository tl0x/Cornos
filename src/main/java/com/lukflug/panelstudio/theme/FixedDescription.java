package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.Context;

import java.awt.*;

/**
 * Renders a description at a fixed position.
 *
 * @author lukflug
 */
public class FixedDescription implements DescriptionRenderer {
    protected Point pos;

    public FixedDescription(Point pos) {
        this.pos = pos;
    }

    @Override
    public void renderDescription(Context context) {
        if (context.getDescription() != null) {
            Rectangle r = new Rectangle(pos,
                    new Dimension(context.getInterface().getFontWidth(context.getDescription()),
                            context.getInterface().getFontHeight()));
            Color bgcolor = new Color(0, 0, 0);
            context.getInterface().fillRect(r, bgcolor, bgcolor, bgcolor, bgcolor);
            Color color = new Color(255, 255, 255);
            context.getInterface().drawRect(r, color, color, color, color);
            context.getInterface().drawString(pos, context.getDescription(), color);
        }
    }
}
