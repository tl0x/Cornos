package me.constantindev.ccl.etc;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class RenderableBlock {
    public final int color;
    public final int r;
    public final int g;
    public final int b;
    public final int a;
    public final BlockPos bp;
    public Vec3d dimensions;

    public RenderableBlock(BlockPos pos, int r, int g, int b, int a) {
        this.color = (a << 24) + (r << 16) + (g << 8) + (b);
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.bp = pos;
        this.dimensions = new Vec3d(1, 1, 1);
    }

    public RenderableBlock(BlockPos pos, int r, int g, int b, int a, Vec3d dim) {
        this(pos, r, g, b, a);
        this.dimensions = dim;
    }
}
