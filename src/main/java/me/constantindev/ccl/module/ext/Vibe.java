package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MConfColor;
import me.constantindev.ccl.etc.config.MConfMultiOption;
import me.constantindev.ccl.etc.config.MConfNum;
import me.constantindev.ccl.etc.config.MConfToggleable;
import me.constantindev.ccl.etc.helper.Rnd;
import me.constantindev.ccl.etc.ms.ModuleType;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

public class Vibe extends Module {
    public static MConfToggleable rgbBlocks = new MConfToggleable("rgbbocks", false);
    public static MConfToggleable fog = new MConfToggleable("fog", true);
    public static MConfMultiOption skyType = new MConfMultiOption("skyType", "overworld", new String[]{"overworld", "nether", "end"});
    public static MConfNum dim = new MConfNum("brightness", 80, 100, 0);
    public static MConfNum ch = new MConfNum("cloudsHeight", 128, 255, 0);
    public static MConfNum rgbnoise = new MConfNum("rgbNoise", 5, 100, 0);
    public static MConfColor blockOutline = new MConfColor("blockOutline", new Color(0, 0, 0));
    static float prevOff = 0;

    public Vibe() {
        super("Vibe", "Changes world render settings", ModuleType.RENDER);
        this.mconf.add(dim);
        this.mconf.add(ch);
        this.mconf.add(fog);
        this.mconf.add(skyType);
        this.mconf.add(blockOutline);
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
        prevOff += Rnd.rndD(4) - 2;
        prevOff = MathHelper.clamp(prevOff, (float) -rgbnoise.getValue(), (float) rgbnoise.getValue());
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
