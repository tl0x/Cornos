package me.constantindev.ccl.etc;

import net.minecraft.util.math.BlockPos;

public class RenderableBlock {
    public final int color;
    public final int r;
    public final int g;
    public final int b;
    public final int a;
    public final BlockPos bp;
    public RenderableBlock(BlockPos pos, int r, int g, int b, int a) {
        this.color = (a << 24) + (r << 16) + (g << 8) + (b);
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.bp = pos;
    }
}
