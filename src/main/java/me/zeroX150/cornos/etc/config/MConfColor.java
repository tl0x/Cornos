/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: RGBAColor
# Created by constantin at 08:04, Mär 17 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.zeroX150.cornos.etc.config;

import me.zeroX150.cornos.etc.helper.STL;

import java.awt.*;

public class MConfColor extends MConf.ConfigKey {
    Color c;
    boolean rainbow = false;

    public MConfColor(String k, Color rgb) {
        super(k, rgb.getRGB() + ":0", "No description");
        this.c = rgb;
    }

    public MConfColor(String k, Color rgb, String d) {
        this(k, rgb);
        this.description = d;
    }

    public Color getColor() {
        Color finalcol = new Color(CConf.latestRGBVal);
        if (!rainbow)
            finalcol = c;
        return finalcol;
    }

    public void setColor(Color newColor) {
        c = newColor;
        String[] p = this.value.split(":");
        this.value = newColor.getRGB() + ":" + p[1];
    }

    public int getRGB() {
        Color finalcol = isRainbow() ? new Color(CConf.latestRGBVal) : c;
        return finalcol.getRGB();
    }

    @Override
    public void setValue(String newV) {
        String[] pair = newV.split(":");
        if (pair.length != 2)
            return;
        String color = pair[0];
        String rgb = pair[1].toLowerCase();
        if (!rgb.equals("1") && !rgb.equals("0"))
            return;
        if (!STL.tryParseI(color))
            return;
        int bruh = Integer.parseInt(color);
        this.setRainbow(rgb.equals("1"));
        this.setColor(new Color(bruh));
        super.setValue(new Color(bruh).getRGB() + ":" + rgb);
    }

    public boolean isRainbow() {
        return rainbow;
    }

    public void setRainbow(boolean isRainbow) {
        rainbow = isRainbow;
        String[] p = this.value.split(":");
        String c = p[0];
        this.value = c + ":" + (rainbow ? "1" : "0");
    }
}
