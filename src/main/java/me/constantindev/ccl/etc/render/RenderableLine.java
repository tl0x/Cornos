/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: RenderableLine
# Created by constantin at 18:13, Mär 03 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.etc.render;

import net.minecraft.util.math.Vec3d;

import java.awt.*;

public class RenderableLine {
    public final Vec3d bp1;
    public final Vec3d bp2;
    public final Color c;
    public final int width;

    public RenderableLine(Vec3d from, Vec3d to, int r, int g, int b, int a, int width) {
        this.bp1 = from;
        this.bp2 = to;
        this.c = new Color(r, g, b, a);
        this.width = width;
    }
}
