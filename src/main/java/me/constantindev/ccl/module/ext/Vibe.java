package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MultiOption;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.config.Toggleable;
import me.constantindev.ccl.etc.helper.RandomHelper;
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

public class Vibe extends Module {
    public static Toggleable rgbBlocks = new Toggleable("rgbbocks", false);
    public static Toggleable fog = new Toggleable("fog", true);
    public static MultiOption skyType = new MultiOption("skyType", "overworld", new String[]{"overworld", "nether", "end"});
    public static Num dim = new Num("brightness", 80, 100, 0);
    public static Num ch = new Num("cloudsHeight", 128, 255, 0);
    public static Num rgbnoise = new Num("rgbNoise", 5, 100, 0);
    static double prevOff = 0;

    public Vibe() {
        super("Vibe", "Changes world render settings", MType.RENDER);
        this.mconf.add(dim);
        this.mconf.add(ch);
        this.mconf.add(fog);
        this.mconf.add(skyType);
        this.mconf.add(rgbBlocks);
        this.mconf.add(rgbnoise);
    }

    public static SkyProperties getProps() {
        SkyProperties.SkyType bruh;
        switch (skyType.value) {
            case "nether":
                bruh = SkyProperties.SkyType.NONE;
                break;
            case "end":
                bruh = SkyProperties.SkyType.END;
                break;
            default:
                bruh = SkyProperties.SkyType.NORMAL;
        }
        return new SkyProperties((float) ch.getValue(), true, bruh, true, dim.getValue() != 100) {
            @Override
            public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
                double mtp = dim.getValue() / 100;
                return color.multiply(mtp);
            }

            @Override
            public boolean useThickFog(int camX, int camY) {
                return fog.isEnabled();
            }
        };
    }

    public static int calculateBP(BlockPos pos) {
        float bruh = (pos.getX() + pos.getY() + pos.getZ()) * 10f;
        prevOff += RandomHelper.rndD(4) - 2;
        prevOff = MathHelper.clamp(prevOff, -rgbnoise.getValue(), rgbnoise.getValue());
        bruh += prevOff;
        bruh = Math.abs(bruh);
        bruh %= 255 * 3;
        int stage = (int) Math.floor(bruh / 255);
        int seed = (int) Math.floor(bruh % 255);
        int r = stage == 0 ? seed : (stage == 1 ? Math.abs(seed - 255) : 0);
        int g = stage == 1 ? seed : (stage == 2 ? Math.abs(seed - 255) : 0);
        int b = stage == 2 ? seed : (stage == 0 ? Math.abs(seed - 255) : 0);
        return new Color(r, g, b).getRGB();
    }
}
