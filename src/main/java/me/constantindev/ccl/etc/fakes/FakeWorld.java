package me.constantindev.ccl.etc.fakes;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class FakeWorld extends ClientWorld {

    public static final FakeWorld WORLD;

    static {
        try {
            Field u = Unsafe.class.getDeclaredField("theUnsafe");
            u.setAccessible(true);
            Unsafe unsafe = (Unsafe) u.get(null);
            WORLD = (FakeWorld) unsafe.allocateInstance(FakeWorld.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public FakeWorld() {
        super(null, null, null, null, 0, null, null, false, -1);
    }

    @Override
    public BlockPos getSpawnPos() {
        return BlockPos.ORIGIN;
    }

    @Override
    public float method_30671() {
        return 0;
    }
}
