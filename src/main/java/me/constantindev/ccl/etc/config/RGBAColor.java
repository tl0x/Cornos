/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: RGBAColor
# Created by constantin at 08:04, Mär 17 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.etc.config;

import me.constantindev.ccl.etc.helper.ClientHelper;

import java.awt.*;

public class RGBAColor extends ModuleConfig.ConfigKey {
    Color c;
    boolean rainbow;

    public RGBAColor(String k, Color rgb) {
        super(k, rgb.getRGB() + "");
        this.c = rgb;
    }

    public Color getColor() {
        Color finalcol = new Color(ClientConfig.latestRGBVal);
        if (!rainbow) finalcol = c;
        return finalcol;
    }

    public void setColor(Color newColor) {
        c = newColor;
    }

    public int getRGB() {
        Color finalcol = new Color(ClientConfig.latestRGBVal);
        if (!rainbow) finalcol = c;
        return finalcol.getRGB();
    }

    @Override
    public void setValue(String newV) {
        if (!ClientHelper.isIntValid(newV)) return;
        int bruh = Integer.parseInt(newV);
        if (bruh > 0xFFFFFF || bruh < 0) return;
        super.setValue(new Color(Integer.parseInt(newV)).getRGB() + "");
    }

    public boolean isRainbow() {
        return rainbow;
    }

    public void setRainbow(boolean isRainbow) {
        rainbow = isRainbow;
    }
}
