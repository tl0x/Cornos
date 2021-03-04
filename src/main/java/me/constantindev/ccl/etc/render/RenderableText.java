/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: RenderableText
# Created by constantin at 22:08, Mär 04 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.etc.render;

import com.sun.javafx.geom.Vec2d;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

public class RenderableText {
    public Vec3d pos;
    public String text;
    public Vec2d size;
    public Color color;

    public RenderableText(Vec3d pos, Vec2d size, String text, Color rgb) {
        this.pos = pos;
        this.size = size;
        this.text = text;
        this.color = rgb;
    }
}
